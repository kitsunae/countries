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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional(readOnly = true)
    public List<Country> getCountriesByName(String name) {
        return countryRepository.findByName(name);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Country> getCountriesByName(String name, Pageable pageRequest) {
        return countryRepository.findByName(name, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Country getCountryByCode(String code) {
        return countryRepository.findOne(code);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Country> getCountriesByCapital(Long cityId) {
        return countryRepository.findByCapitalId(cityId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Country> getCountriesByCapital(Long cityId, Pageable pageRequest) {
        return countryRepository.findByCapitalId(cityId, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Country> getCountriesByCapital(String capitalName) {
        return countryRepository.findByCapitalName(capitalName);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Country> getCountriesByCapital(String capitalName, Pageable pageRequest) {
        return countryRepository.findByCapitalName(capitalName, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Country> getAllCountries(Pageable pageRequest) {
        return countryRepository.findAll(pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllCountryNames() {
        return countryRepository.findAllCountryNames();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<String> getAllCountryNames(Pageable pageRequest) {
        return countryRepository.findAllCountryNames(pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Country> getCountriesByContinent(Continent continent) {
        return countryRepository.findByContinent(continent);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Country> getCountriesByContinent(Continent continent, Pageable pageRequest) {
        return countryRepository.findByContinent(continent, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Country> getCountriesByContinentName(String continentName) {
        Continent continent = Continent.fromString(continentName);
        return getCountriesByContinent(continent);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Country> getCountriesByContinentName(String continentName, Pageable pageRequest) {
        Continent continent = Continent.fromString(continentName);
        return getCountriesByContinent(continent, pageRequest);
    }

    //TODO edit
    @Override
    @Transactional(readOnly = true)
    public List<Country> getCountriesByLanguage(String language) {
        return languageRepository.findByLanguage(language)
                .stream()
                .map(CountryLanguage::getCountry)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional
    public Country save(Country country) {
        return countryRepository.save(country);
    }

    @Override
    @Transactional
    public Country edit(Country country, String countryCode) {
        if (!country.getCode().equals(countryCode))
            return null; //TODO throw Exception
        return save(country);
    }

    @Override
    @Transactional
    public void remove(Country country) {
        countryRepository.delete(country);
    }

    @Override
    @Transactional
    public void remove(String countryCode) {
        countryRepository.delete(countryCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Country> filterCountries(CountryFilter filter) {
        return countryRepository.filterCountries(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Country> filterCountries(CountryFilter filter, Pageable pageRequest) {
        List<Country> list = filterCountries(filter);
        List<Country> result = list.stream()
                .skip(pageRequest.getPageNumber()*pageRequest.getPageSize())
                .limit(pageRequest.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(result, pageRequest, list.size());
    }
}
