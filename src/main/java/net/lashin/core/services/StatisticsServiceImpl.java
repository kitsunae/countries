package net.lashin.core.services;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Country;
import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.dao.CityRepository;
import net.lashin.core.dao.CountryLanguageRepository;
import net.lashin.core.dao.CountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsServiceImpl.class);
    private CountryRepository countryRepository;
    private CityRepository cityRepository;
    private CountryLanguageRepository languageRepository;

    @Autowired
    public StatisticsServiceImpl(CountryRepository countryRepository, CityRepository cityRepository, CountryLanguageRepository languageRepository) {
        LOGGER.debug("Instantiating StatisticsServiceImpl");
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.languageRepository = languageRepository;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public List<Country> getBiggestCountries(int quantity) {
        LOGGER.debug("Get {} bigges countries", quantity);
        PageRequest pageRequest = new PageRequest(0, quantity, Sort.Direction.DESC, "geography.surfaceArea");
        return countryRepository.findAll(pageRequest).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public Page<Country> getBiggestCountries(Pageable pageRequest) {
        LOGGER.debug("Get {} biggest countries, page #{}", pageRequest.getPageSize(), pageRequest.getPageNumber());
        PageRequest ordered = new PageRequest(pageRequest.getPageNumber(), pageRequest.getPageSize(), Sort.Direction.DESC, "geography.surfaceArea");
        return countryRepository.findAll(ordered);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countrylanguages")
    public Map<String, Long> getMostCommonLanguages(int quantity) {
        LOGGER.debug("Get {} most common languages", quantity);
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
        LOGGER.debug("Get languages paired by number of speakers");
        return languageRepository.findAll().stream()
                    .collect(Collectors.groupingBy(CountryLanguage::getLanguage,
                            Collectors.summingDouble(lang -> lang.getCountry().getDemography().getPopulation() * lang.getPercentage() / 100)));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countrylanguages")
    public Page<Map<String, Long>> getMostCommonLanguages(Pageable pageRequest) {
        LOGGER.debug("Get {} most common languages, page #{}", pageRequest.getPageSize(), pageRequest.getPageNumber());
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
    @Transactional(readOnly = true)
    @Cacheable("cities")
    public List<City> getBiggestCities(int quantity) {
        LOGGER.debug("Get {} biggest cities", quantity);
        PageRequest pageRequest = new PageRequest(0,quantity, new Sort(Sort.Direction.DESC, "population"));
        return cityRepository.findAll(pageRequest).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("cities")
    public Page<City> getBiggestCities(Pageable pageRequest) {
        LOGGER.debug("Get {} biggest cities, page #{}", pageRequest.getPageSize(), pageRequest.getPageNumber());
        PageRequest ordered = new PageRequest(pageRequest.getPageNumber(), pageRequest.getPageSize(), Sort.Direction.DESC, "population");
        return cityRepository.findAll(ordered);
    }

    @Override
    @Transactional(readOnly = true)
    public double getPercentageOfUrbanPopulationOfCountry(String countryCode) {
        LOGGER.debug("Get percentage of urban population of country {}", countryCode);
        double cityPopulation = cityRepository.findByCountryCode(countryCode)
                .stream()
                .collect(Collectors.summingLong(City::getPopulation));
        return cityPopulation / countryRepository.findOne(countryCode).getDemography().getPopulation() * 100;
    }

    @Override
    @Transactional(readOnly = true)
    public long getWorldPopulation() {
        LOGGER.debug("Get world population");
        return countryRepository.findAll().stream().collect(Collectors.summingLong(c -> c.getDemography().getPopulation()));
    }
}
