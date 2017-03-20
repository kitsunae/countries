package net.lashin.core.services;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Country;
import net.lashin.core.filters.CountryFilter;

import java.util.List;
import java.util.Map;

/**
 * Created by lashi on 18.03.2017.
 */
public interface StatisticsService {
    List<Country> getBiggestCountries(int quantity);
    Map<String,Long> getMostCommonLanguages(int quantity);
    List<City> getBiggestCities(int quantity);
    double getPercentageOfUrbanPopulationOfCountry(String countryCode);
    long getWorldPopulation();
    List<Country> filterCountries(CountryFilter filter);
}
