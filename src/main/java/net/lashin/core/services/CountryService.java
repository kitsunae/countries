package net.lashin.core.services;

import net.lashin.core.beans.Continent;
import net.lashin.core.beans.Country;
import net.lashin.core.filters.CountryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CountryService {
    List<Country> getByName(String name);

    Page<Country> getByName(String name, Pageable pageRequest);

    Country getByCode(String code);

    List<Country> getByCapital(Long cityId);

    Page<Country> getByCapital(Long cityId, Pageable pageRequest);

    List<Country> getByCapital(String name);

    Page<Country> getByCapital(String capitalName, Pageable pageRequest);

    List<Country> getAll();

    Page<Country> getAll(Pageable pageRequest);
    List<String> getAllCountryNames();
    Page<String> getAllCountryNames(Pageable pageRequest);

    List<Country> getByContinent(Continent continent);

    Page<Country> getByContinent(Continent continent, Pageable pageRequest);

    List<Country> getByContinentName(String continentName);

    Page<Country> getByContinentName(String continentName, Pageable pageRequest);

    List<Country> getByLanguage(String language);

    Page<Country> getByLanguage(String language, Pageable pageRequest);
    Country save(Country country);
    Country edit(Country country, String countryCode);
    void remove(Country country);
    void remove(String countryCode);

    List<Country> filter(CountryFilter filter);

    Page<Country> filter(CountryFilter filter, Pageable pageRequest);
}
