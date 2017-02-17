package net.lashin.services;

import net.lashin.beans.*;
import net.lashin.dao.CityRepository;
import net.lashin.dao.CountryLanguageRepository;
import net.lashin.dao.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lashi on 09.02.2017.
 */
@Service
@Transactional
public class WorldServiceImpl implements WorldService{
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CountryLanguageRepository languageRepository;

    @Override
    public City getCityByName(String name) {
        return cityRepository.findByName(name);
    }

    @Override
    public List<City> getCitiesByCountry(Country country) {
        return cityRepository.findByCountry(country);
    }

    @Override
    public List<CountryLanguage> getAllLanguages() {
        return languageRepository.findAll();
    }

    @Override
    public Country getCountryByName(String name) {
        return countryRepository.findByName(name);
    }

    @Override
    public List<City> getWorldCapitals() {
        return countryRepository.findAll().stream().map(Country::getCapital).collect(Collectors.toList());
    }

    @Override
    public List<CountryLanguage> getLanguagesByCountryCode(String countryId) {
        return countryRepository.findOne(countryId).getLanguages();
    }

    @Override
    public Country getCountryByCode(String code) {
        return countryRepository.findOne(code);
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public City save(City city) {
        return cityRepository.saveAndFlush(city);
    }

    @Override
    public City save(City city, String countryCode) {
        Country country = countryRepository.findOne(countryCode);
        city.setCountry(country);
        return cityRepository.saveAndFlush(city);
    }

    @Override
    public Country getCountryByCapital(City capital) {
        return countryRepository.findByCapital(capital.getId());
    }

    @Override
    public List<CountryLanguage> getLanguagesByOfficialty(boolean isOfficial) {
        return languageRepository.findByIsOfficial(isOfficial);
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public CountryLanguage getLanguageByNameAndCountry(String language, String countryCode) {
        CountryLanguageId id = new CountryLanguageId(countryCode, language);
        return languageRepository.findOne(id);
    }

    @Override
    public List<String> getAllCountryNames() {
        return countryRepository.findAll().stream().map(Country::getName).collect(Collectors.toList());
    }

    @Override
    public List<Country> getCountriesByContinent(Continent continent) {
        return countryRepository.findByContinent(continent);
    }

    @Override
    public List<CountryLanguage> getLanguagesByPercentRate(Double percentage, boolean less) {
        List<CountryLanguage> languages = new ArrayList<>();
        for (CountryLanguage language : languageRepository.findAll()){
            if (language.getPercentage().compareTo(percentage)<=0 && less) {
                languages.add(language);
            }else if (language.getPercentage().compareTo(percentage)>=0 && !less){
                languages.add(language);
            }
        }
        return languages;
    }

    @Override
    public List<Country> getCountriesByLanguage(String language) {
        return languageRepository.findAll().stream().filter(countryLanguage -> countryLanguage.getLanguage().equals(language)).map(CountryLanguage::getCountry).collect(Collectors.toList());
    }

    @Override
    public CountryLanguage save(CountryLanguage language) {
        return languageRepository.saveAndFlush(language);
    }

    //TODO refactor lazy initialization
    @Override
    public Country save(Country country) {
        Country oldCountry = countryRepository.findOne(country.getCode());
        if (oldCountry!=null){
            List<City> cities = oldCountry.getCities();
            List<CountryLanguage> languages = oldCountry.getLanguages();
            country.addCities(cities);
            country.addLanguages(languages);
            return countryRepository.saveAndFlush(country);
        }
        return countryRepository.saveAndFlush(country);
    }

    @Override
    public void remove(Country country) {
        countryRepository.delete(country);
    }

    @Override
    public List<City> getCitiesByCountryCode(String countryCode) {
        return getCitiesByCountry(getCountryByCode(countryCode));
    }

    @Override
    public List<CountryLanguage> getLanguagesByCountry(Country country) {
        return languageRepository.findByCountry(country);
    }
}
