package net.lashin.core.services;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Country;
import net.lashin.core.filters.CityFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CityService {
    List<City> getByName(String name);

    Page<City> getByName(String name, Pageable pageRequest);
    List<City> getWorldCapitals();
    Page<City> getWorldCapitals(Pageable pageRequest);

    List<City> getAll();

    Page<City> getAll(Pageable pageRequest);
    City edit(City city, Long cityId, String countryCode);
    City save(City city, String countryCode);

    List<City> getByCountryCode(String countryCode);

    Page<City> getByCountryCode(String countryCode, Pageable pageRequest);

    List<City> getByCountry(Country country);

    Page<City> getByCountry(Country country, Pageable pageRequest);

    City getById(long id);
    void remove(Long id);

    List<City> filter(CityFilter filter);

    Page<City> filter(CityFilter filter, Pageable pageRequest);
}
