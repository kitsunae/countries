package net.lashin.core.services;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Country;
import net.lashin.core.dao.CityRepository;
import net.lashin.core.dao.CountryRepository;
import net.lashin.core.filters.CityFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);
    private CityRepository cityRepository;
    private CountryRepository countryRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository) {
        LOGGER.debug("Instantiation CityServiceImpl");
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<City> getByName(String name) {
        LOGGER.debug("Get by name {}", name);
        return cityRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<City> getByName(String name, Pageable pageRequest) {
        LOGGER.debug("Get by name {}, page {} amount {}", name, pageRequest.getPageNumber(), pageRequest.getPageSize());
        return cityRepository.findByName(name, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<City> getWorldCapitals() {
        LOGGER.debug("Get all world capitals");
        return countryRepository.findAll()
                .stream()
                .map(Country::getCapital)
                .filter(city -> city!=null)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<City> getWorldCapitals(Pageable pageRequest) {
        LOGGER.debug("Get {} world capitals, page#{}", pageRequest.getPageSize(), pageRequest.getPageNumber());
        List<City> all = getWorldCapitals();
        List<City> result = all.stream()
                .skip(pageRequest.getPageNumber()*pageRequest.getPageSize())
                .limit(pageRequest.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(result, pageRequest, all.size());
    }

    @Override
    @Transactional(readOnly = true)
    public List<City> getAll() {
        LOGGER.debug("Get all cities");
        return cityRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<City> getAll(Pageable pageRequest) {
        LOGGER.debug("Get {} cities, page #{}", pageRequest.getPageSize(), pageRequest.getPageNumber());
        return cityRepository.findAll(pageRequest);
    }

    @Override
    @Transactional
    public City edit(City city, Long cityId, String countryCode) {
        LOGGER.debug("Edit city {} with id {} of country {}", city, cityId, countryCode);
        if (!Objects.equals(city.getId(), cityId))
            return null; //TODO throw exception
        Country country = countryRepository.findOne(countryCode);
        city.setCountry(country);
        return cityRepository.save(city);
    }

    @Override
    @Transactional
    public City save(City city, String countryCode) {
        LOGGER.debug("Saving city {} of country {}", city, countryCode);
        Country country = countryRepository.findOne(countryCode);
        city.setCountry(country);
        return cityRepository.save(city);
    }

    @Override
    @Transactional(readOnly = true)
    public List<City> getByCountryCode(String countryCode) {
        LOGGER.debug("Get cities by country code {}", countryCode);
        return cityRepository.findByCountryCode(countryCode);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<City> getByCountryCode(String countryCode, Pageable pageRequest) {
        LOGGER.debug("Get {} cities by country code {}, page {}", pageRequest.getPageSize(), countryCode, pageRequest.getPageNumber());
        return cityRepository.findByCountryCode(countryCode, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<City> getByCountry(Country country) {
        LOGGER.debug("Get cities of country {}", country);
        return cityRepository.findByCountryCode(country.getCode());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<City> getByCountry(Country country, Pageable pageRequest) {
        LOGGER.debug("Get {} cities of country {}, page #{}", pageRequest.getPageSize(), country, pageRequest.getPageNumber());
        return cityRepository.findByCountryCode(country.getCode(), pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public City getById(long id) {
        LOGGER.debug("Get city by id {}", id);
        return cityRepository.findOne(id);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        LOGGER.debug("Delete city with id {}", id);
        List<Country> countries = countryRepository.findByCapitalId(id);
        countries.forEach(country -> country.setCapital(null));
        countryRepository.save(countries);
        cityRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<City> filter(CityFilter filter) {
        LOGGER.debug("Filter cities, filter {}", filter);
        return cityRepository.filterCities(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<City> filter(CityFilter filter, Pageable pageRequest) {
        LOGGER.debug("Filter cities, filter {}, {} of page #{}", filter, pageRequest.getPageSize(), pageRequest.getPageNumber());
        List<City> all = filter(filter);
        List<City> result = all.stream()
                .skip(pageRequest.getPageNumber()*pageRequest.getPageSize())
                .limit(pageRequest.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(result, pageRequest, all.size());
    }
}
