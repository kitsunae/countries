package net.lashin.core.services;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface StatisticsService {
    List<Country> getBiggestCountries(int quantity);
    Page<Country> getBiggestCountries(Pageable pageRequest);
    Map<String,Long> getMostCommonLanguages(int quantity);
    Page<Map<String,Long>> getMostCommonLanguages(Pageable pageRequest);
    List<City> getBiggestCities(int quantity);
    Page<City> getBiggestCities(Pageable pageRequest);
    double getPercentageOfUrbanPopulationOfCountry(String countryCode);
    long getWorldPopulation();
}
