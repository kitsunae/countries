package net.lashin.services;

import net.lashin.beans.City;
import net.lashin.beans.Country;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by lashi on 15.02.2017.
 */
public interface CityService {
    City getCityByName(String name);
    List<City> getWorldCapitals();
    List<City> getAllCities();
    City save(@Valid City city);
    City save(City city, String countryCode);
    List<City> getCitiesByCountryCode(String countryCode);
}
