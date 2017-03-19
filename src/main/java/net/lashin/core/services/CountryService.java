package net.lashin.core.services;

import net.lashin.core.beans.Continent;
import net.lashin.core.beans.Country;

import java.util.List;

/**
 * Created by lashi on 15.02.2017.
 */
public interface CountryService {
    List<Country> getCountriesByName(String name);
    Country getCountryByCode(String code);
    List<Country> getCountriesByCapital(Long cityId);
    List<Country> getCountriesByCapital(String name);
    List<Country> getAllCountries();
    List<String> getAllCountryNames();
    List<Country> getCountriesByContinent(Continent continent);
    List<Country> getCountriesByContinentName(String continentName);
    List<Country> getCountriesByLanguage(String language);
    Country save(Country country);
    Country edit(Country country, String countryCode);
    void remove(Country country);
    void remove(String countryCode);
}