package net.lashin.core.services;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Country;
import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.dao.CityRepository;
import net.lashin.core.dao.CountryLanguageRepository;
import net.lashin.core.dao.CountryRepository;
import net.lashin.core.filters.CountryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lashi on 18.03.2017.
 */
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
    public Map<String, Long> getMostCommonLanguages(int quantity) {
        Map<String, Double> map = languageRepository.findAll().stream()
                .collect(Collectors.groupingBy(CountryLanguage::getLanguage,
                        Collectors.summingDouble(((CountryLanguage lang)->lang.getCountry().getPopulation()*lang.getPercentage()/100))));
        List<Double> values = new ArrayList<>(map.values());
        Collections.sort(values, (o1, o2) -> o2.compareTo(o1));
        Map<String, Long> result = new HashMap<>();
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

    @Override
    public List<City> getBiggestCities(int quantity) {
        PageRequest pageRequest = new PageRequest(0,quantity, new Sort(Sort.Direction.DESC, "population"));
        return cityRepository.findAll(pageRequest).getContent();
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

    @Override
    public List<Country> filterCountries(CountryFilter filter) {
        List<Country> queryResults = countryRepository.filterCountries(filter.getMinSurfaceArea(), filter.getMaxSurfaceArea(),
                filter.getMinIndepYear(), filter.getMaxIndepYear(),
                filter.getMinPopulation(), filter.getMaxPopulation(),
                filter.getMinLifeExpectancy(), filter.getMaxLifeExpectancy(),
                filter.getMinGnp(), filter.getMaxGnp(),
                filter.getMinGnpOld(), filter.getMaxGnpOld());
        return queryResults.stream()
                .filter(country -> !(country.getIndepYear()==null && filter.isEnabledYearFilter()))
                .filter(country -> !(country.getLifeExpectancy()==null && filter.isEnabledLifeExpectFilter()))
                .filter(country -> !(country.getGnp()==null && filter.isEnabledGnpFilter()))
                .filter(country -> !(country.getGnpOld()==null && filter.isEnabledGnpOldFilter()))
                .filter(country -> filter.getContinent() == null || country.getContinent()==filter.getContinent())
                .filter(country -> filter.getRegion()==null || filter.getRegion().equals(country.getRegion()))
                .filter(country -> filter.getGovernmentForm()==null|| filter.getGovernmentForm().equals(country.getGovernmentForm()))
                .collect(Collectors.toList());
    }
}
