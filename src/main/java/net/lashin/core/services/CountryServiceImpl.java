package net.lashin.core.services;

import net.lashin.core.beans.Continent;
import net.lashin.core.beans.Country;
import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.dao.CountryLanguageRepository;
import net.lashin.core.dao.CountryRepository;
import net.lashin.core.filters.CountryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lashi on 14.03.2017.
 */
@Service
public class CountryServiceImpl implements CountryService{

    private CountryRepository countryRepository;
    private CountryLanguageRepository languageRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, CountryLanguageRepository languageRepository) {
        this.countryRepository = countryRepository;
        this.languageRepository = languageRepository;
    }

    @Override
    public List<Country> getCountriesByName(String name) {
        return countryRepository.findByName(name);
    }


    @Override
    public Page<Country> getCountriesByName(String name, Pageable pageRequest) {
        return countryRepository.findByName(name, pageRequest);
    }

    @Override
    public Country getCountryByCode(String code) {
        return countryRepository.findOne(code);
    }

    @Override
    public List<Country> getCountriesByCapital(Long cityId) {
        return countryRepository.findByCapitalId(cityId);
    }

    @Override
    public Page<Country> getCountriesByCapital(Long cityId, Pageable pageRequest) {
        return countryRepository.findByCapitalId(cityId, pageRequest);
    }

    @Override
    public List<Country> getCountriesByCapital(String capitalName) {
        return countryRepository.findByCapitalName(capitalName);
    }

    @Override
    public Page<Country> getCountriesByCapital(String capitalName, Pageable pageRequest) {
        return countryRepository.findByCapitalName(capitalName, pageRequest);
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Page<Country> getAllCountries(Pageable pageRequest) {
        return countryRepository.findAll(pageRequest);
    }

    @Override
    public List<String> getAllCountryNames() {
        return countryRepository.findAllCountryNames();
    }

    @Override
    public Page<String> getAllCountryNames(Pageable pageRequest) {
        return countryRepository.findAllCountryNames(pageRequest);
    }

    @Override
    public List<Country> getCountriesByContinent(Continent continent) {
        return countryRepository.findByContinent(continent);
    }

    @Override
    public Page<Country> getCountriesByContinent(Continent continent, Pageable pageRequest) {
        return countryRepository.findByContinent(continent, pageRequest);
    }

    @Override
    public List<Country> getCountriesByContinentName(String continentName) {
        Continent continent = Continent.fromString(continentName);
        return getCountriesByContinent(continent);
    }

    @Override
    public Page<Country> getCountriesByContinentName(String continentName, Pageable pageRequest) {
        Continent continent = Continent.fromString(continentName);
        return getCountriesByContinent(continent, pageRequest);
    }

    //TODO edit
    @Override
    public List<Country> getCountriesByLanguage(String language) {
        return languageRepository.findByLanguage(language)
                .stream()
                .map(CountryLanguage::getCountry)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Country> getCountriesByLanguage(String language, Pageable pageRequest) {
        List<Country> list = getCountriesByLanguage(language);
        List<Country> result = list
                .stream()
                .skip(pageRequest.getPageNumber()*pageRequest.getPageSize())
                .limit(pageRequest.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(result, pageRequest, list.size());
    }

    @Override
    public Country save(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country edit(Country country, String countryCode) {
        if (!country.getCode().equals(countryCode))
            return null; //TODO throw Exception
        return save(country);
    }

    @Override
    public void remove(Country country) {
        countryRepository.delete(country);
    }

    @Override
    public void remove(String countryCode) {
        countryRepository.delete(countryCode);
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

    @Override
    public Page<Country> filterCountries(CountryFilter filter, Pageable pageRequest) {
        List<Country> list = filterCountries(filter);
        List<Country> result = list.stream()
                .skip(pageRequest.getPageNumber()*pageRequest.getPageSize())
                .limit(pageRequest.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(result, pageRequest, list.size());
    }
}
