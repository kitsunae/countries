package net.lashin.core.services;

import net.lashin.core.beans.Continent;
import net.lashin.core.beans.Country;
import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.dao.CountryLanguageRepository;
import net.lashin.core.dao.CountryRepository;
import net.lashin.core.filters.CountryFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryServiceImpl.class);
    private CountryRepository countryRepository;
    private CountryLanguageRepository languageRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, CountryLanguageRepository languageRepository) {
        LOGGER.debug("Instantiating CountryServiceImpl");
        this.countryRepository = countryRepository;
        this.languageRepository = languageRepository;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public List<Country> getByName(String name) {
        LOGGER.debug("Get countries by name {}", name);
        return countryRepository.findByName(name);
    }


    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public Page<Country> getByName(String name, Pageable pageRequest) {
        LOGGER.debug("Get {} countries by name {}, page #{}", pageRequest.getPageSize(), name, pageRequest.getPageNumber());
        return countryRepository.findByName(name, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public Country getByCode(String code) {
        LOGGER.debug("Get country by code {}", code);
        return countryRepository.findCountryWithImages(code);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public List<Country> getByCapital(Long cityId) {
        LOGGER.debug("Get countries by capital id {}", cityId);
        return countryRepository.findByCapitalId(cityId);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public Page<Country> getByCapital(Long cityId, Pageable pageRequest) {
        LOGGER.debug("Get {} countries by capital id {}, page #{}", pageRequest.getPageSize(), cityId, pageRequest.getPageNumber());
        return countryRepository.findByCapitalId(cityId, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public List<Country> getByCapital(String capitalName) {
        LOGGER.debug("Get countries by capital name {}", capitalName);
        return countryRepository.findByCapitalName(capitalName);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public Page<Country> getByCapital(String capitalName, Pageable pageRequest) {
        LOGGER.debug("Get {} countries by capital name {}, page #{}", pageRequest.getPageSize(), capitalName, pageRequest.getPageNumber());
        return countryRepository.findByCapitalName(capitalName, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public List<Country> getAll() {
        LOGGER.debug("Get all countries");
        return countryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public Page<Country> getAll(Pageable pageRequest) {
        LOGGER.debug("Get {} countries, page #{}", pageRequest.getPageSize(), pageRequest.getPageNumber());
        return countryRepository.findAll(pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllCountryNames() {
        LOGGER.debug("Get all country names");
        return countryRepository.findAllCountryNames();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<String> getAllCountryNames(Pageable pageRequest) {
        LOGGER.debug("Get {} country names, page #{}", pageRequest.getPageSize(), pageRequest.getPageNumber());
        return countryRepository.findAllCountryNames(pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public List<Country> getByContinent(Continent continent) {
        LOGGER.debug("Get all countries by continent {}", continent);
        return countryRepository.findByGeographyContinent(continent);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public Page<Country> getByContinent(Continent continent, Pageable pageRequest) {
        LOGGER.debug("Get {} countries by continent {}, page#{}", pageRequest.getPageSize(), continent, pageRequest.getPageNumber());
        return countryRepository.findByGeographyContinent(continent, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public List<Country> getByContinentName(String continentName) {
        LOGGER.debug("Get all countries by continent name {}", continentName);
        Continent continent = Continent.fromString(continentName);
        return getByContinent(continent);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public Page<Country> getByContinentName(String continentName, Pageable pageRequest) {
        LOGGER.debug("Get {} countries by continent name {}, page#{}", pageRequest.getPageSize(), continentName, pageRequest.getPageNumber());
        Continent continent = Continent.fromString(continentName);
        return getByContinent(continent, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public List<Country> getByLanguage(String language) {
        LOGGER.debug("Get all countries by language {}", language);
        return languageRepository.findWithCountriesByLanguage(language)
                .stream()
                .map(CountryLanguage::getCountry)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public Page<Country> getByLanguage(String language, Pageable pageRequest) {
        LOGGER.debug("Get {} countries by language {}, page #{}", pageRequest.getPageSize(), language, pageRequest.getPageNumber());
        List<Country> list = getByLanguage(language);
        List<Country> result = list
                .stream()
                .skip(pageRequest.getPageNumber()*pageRequest.getPageSize())
                .limit(pageRequest.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(result, pageRequest, list.size());
    }

    @Override
    @Transactional
    @CacheEvict(value = {"countries", "cities", "countrylanguages"}, allEntries = true)
    public Country save(Country country) {
        LOGGER.debug("Save country {}", country);
        if (countryRepository.findOne(country.getCode()) != null) {
            throw new IllegalArgumentException("This country code already exists");
        }
        return countryRepository.save(country);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"countries", "cities", "countrylanguages"}, allEntries = true)
    public Country edit(Country country, String countryCode) {
        LOGGER.debug("Edit country {} with code {}", country, countryCode);
        if (!country.getCode().equals(countryCode))
            throw new IllegalArgumentException("Country code not consistent");
        return countryRepository.save(country);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"countries", "cities", "countrylanguages"}, allEntries = true)
    public void remove(Country country) {
        LOGGER.debug("Remove country {}", country);
        countryRepository.delete(country);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"countries", "cities", "countrylanguages"}, allEntries = true)
    public void remove(String countryCode) {
        LOGGER.debug("Remove country by code {}", countryCode);
        countryRepository.delete(countryCode);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public List<Country> filter(CountryFilter filter) {
        LOGGER.debug("Filter all countries, filter {}", filter);
        return countryRepository.filterCountries(filter);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("countries")
    public Page<Country> filter(CountryFilter filter, Pageable pageRequest) {
        LOGGER.debug("Filter countries, filter {}, amount {} page#{}", filter, pageRequest.getPageSize(), pageRequest.getPageNumber());
        List<Country> list = filter(filter);
        List<Country> result = list.stream()
                .skip(pageRequest.getPageNumber()*pageRequest.getPageSize())
                .limit(pageRequest.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(result, pageRequest, list.size());
    }
}
