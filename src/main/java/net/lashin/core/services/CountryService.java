package net.lashin.core.services;

import net.lashin.core.beans.Continent;
import net.lashin.core.beans.Country;
import net.lashin.core.filters.CountryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CountryService {
    List<Country> getCountriesByName(String name);
    Page<Country> getCountriesByName(String name, Pageable pageRequest);
    Country getCountryByCode(String code);
    List<Country> getCountriesByCapital(Long cityId);
    Page<Country> getCountriesByCapital(Long cityId, Pageable pageRequest);
    List<Country> getCountriesByCapital(String name);
    Page<Country> getCountriesByCapital(String capitalName, Pageable pageRequest);
    List<Country> getAllCountries();
    Page<Country> getAllCountries(Pageable pageRequest);
    List<String> getAllCountryNames();
    Page<String> getAllCountryNames(Pageable pageRequest);
    List<Country> getCountriesByContinent(Continent continent);
    Page<Country> getCountriesByContinent(Continent continent, Pageable pageRequest);
    List<Country> getCountriesByContinentName(String continentName);
    Page<Country> getCountriesByContinentName(String continentName, Pageable pageRequest);
    List<Country> getCountriesByLanguage(String language);
    Page<Country> getCountriesByLanguage(String language, Pageable pageRequest);
    Country save(Country country);
    Country edit(Country country, String countryCode);
    void remove(Country country);
    void remove(String countryCode);
    List<Country> filterCountries(CountryFilter filter);
    Page<Country> filterCountries(CountryFilter filter, Pageable pageRequest);
}
