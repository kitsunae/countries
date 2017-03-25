package net.lashin.core.services;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Country;
import net.lashin.core.filters.CityFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CityService {
    List<City> getCitiesByName(String name);
    Page<City> getCitiesByName(String name, Pageable pageRequest);
    List<City> getWorldCapitals();
    Page<City> getWorldCapitals(Pageable pageRequest);
    List<City> getAllCities();
    Page<City> getAllCities(Pageable pageRequest);
    City edit(City city, Long cityId, String countryCode);
    City save(City city, String countryCode);
    List<City> getCitiesByCountryCode(String countryCode);
    Page<City> getCitiesByCountryCode(String countryCode, Pageable pageRequest);
    List<City> getCitiesByCountry(Country country);
    Page<City> getCitiesByCountry(Country country, Pageable pageRequest);
    City getCityById(long id);
    void remove(Long id);
    List<City> filterCities(CityFilter filter);
    Page<City> filterCities(CityFilter filter, Pageable pageRequest);
}
