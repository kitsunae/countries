package net.lashin.core.services;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Country;
import net.lashin.core.filters.CityFilter;

import java.util.List;

/**
 * Created by lashi on 15.02.2017.
 */
public interface CityService {
    List<City> getCitiesByName(String name);
    List<City> getWorldCapitals();
    List<City> getAllCities();
    City edit(City city, Long cityId, String countryCode);
    City save(City city, String countryCode);
    List<City> getCitiesByCountryCode(String countryCode);
    List<City> getCitiesByCountry(Country country);
    City getCityById(long id);
    void remove(Long id);
    List<City> filterCities(CityFilter filter);
}
