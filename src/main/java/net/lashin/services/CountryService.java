package net.lashin.services;

import net.lashin.beans.City;
import net.lashin.beans.Continent;
import net.lashin.beans.Country;
import net.lashin.beans.CountryLanguage;

import java.util.List;

/**
 * Created by lashi on 15.02.2017.
 */
public interface CountryService {
    Country getCountryByName(String name);
    Country getCountryByCode(String code);
    Country getCountryByCapital(City capital);
    List<Country> getAllCountries();
    List<String> getAllCountryNames();
    List<Country> getCountriesByContinent(Continent continent);
    List<Country> getCountriesByLanguage(String language);
    Country save(Country country);
    void remove(Country country);
}
