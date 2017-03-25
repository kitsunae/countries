package net.lashin.core.services;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Country;
import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.dao.CityRepository;
import net.lashin.core.dao.CountryLanguageRepository;
import net.lashin.core.dao.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private CountryRepository countryRepository;
    private CityRepository cityRepository;
    private CountryLanguageRepository languageRepository;

    @Autowired
    public StatisticsServiceImpl(CountryRepository countryRepository, CityRepository cityRepository, CountryLanguageRepository languageRepository) {
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.languageRepository = languageRepository;
    }

    @Override
    public List<Country> getBiggestCountries(int quantity) {
        PageRequest pageRequest = new PageRequest(0, quantity, Sort.Direction.DESC, "surfaceArea");
        return countryRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Page<Country> getBiggestCountries(Pageable pageRequest) {
        PageRequest ordered = new PageRequest(pageRequest.getPageNumber(), pageRequest.getPageSize(), Sort.Direction.DESC, "surfaceArea");
        return countryRepository.findAll(ordered);
    }

    @Override
    public Map<String, Long> getMostCommonLanguages(int quantity) {
        Map<String, Double> map = getLanguagesWithNumberOfSpeakers();
        List<Double> values = new ArrayList<>(map.values());
        Collections.sort(values, (o1, o2) -> o2.compareTo(o1));
        Map<String, Long> result = new LinkedHashMap<>();
        for (int i = 0; i<quantity; ++i){
            Iterator<Map.Entry<String, Double>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Double> pair = iterator.next();
                if (pair.getValue().equals(values.get(i))){
                    result.put(pair.getKey(), pair.getValue().longValue());
                    iterator.remove();
                    break;
                }
            }
        }
        return result;
    }

    private Map<String, Double> getLanguagesWithNumberOfSpeakers() {
        return languageRepository.findAll().stream()
                    .collect(Collectors.groupingBy(CountryLanguage::getLanguage,
                            Collectors.summingDouble(lang->lang.getCountry().getPopulation()*lang.getPercentage()/100)));
    }

    @Override
    public Page<Map<String, Long>> getMostCommonLanguages(Pageable pageRequest) {
        Map<String, Double> map = getLanguagesWithNumberOfSpeakers();
        List<Double> values = new ArrayList<>(map.values());
        Collections.sort(values, (o1, o2) -> o2.compareTo(o1));
        List<Map<String, Long>> languagesSortedBySpeakers = new LinkedList<>();
        for (int i = 0; i<values.size(); ++i){
            Iterator<Map.Entry<String, Double>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Double> pair = iterator.next();
                if (pair.getValue().equals(values.get(i))){
                    languagesSortedBySpeakers.add(i, new HashMap<>());
                    languagesSortedBySpeakers.get(i).put(pair.getKey(), pair.getValue().longValue());
                    iterator.remove();
                    break;
                }
            }
        }
        List<Map<String, Long>> result = languagesSortedBySpeakers.stream()
                .skip(pageRequest.getPageNumber()*pageRequest.getPageSize())
                .limit(pageRequest.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(result, pageRequest, languagesSortedBySpeakers.size());
    }



    @Override
    public List<City> getBiggestCities(int quantity) {
        PageRequest pageRequest = new PageRequest(0,quantity, new Sort(Sort.Direction.DESC, "population"));
        return cityRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Page<City> getBiggestCities(Pageable pageRequest) {
        PageRequest ordered = new PageRequest(pageRequest.getPageNumber(), pageRequest.getPageSize(), Sort.Direction.DESC, "population");
        return cityRepository.findAll(ordered);
    }

    @Override
    public double getPercentageOfUrbanPopulationOfCountry(String countryCode) {
        double cityPopulation = cityRepository.findByCountryCode(countryCode)
                .stream()
                .collect(Collectors.summingLong(City::getPopulation));
        return cityPopulation/countryRepository.findOne(countryCode).getPopulation()*100;
    }

    @Override
    public long getWorldPopulation() {
        return countryRepository.findAll().stream().collect(Collectors.summingLong(Country::getPopulation));
    }
}
