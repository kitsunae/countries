package net.lashin.core.services;

import net.lashin.config.RootConfig;
import net.lashin.core.beans.City;
import net.lashin.core.beans.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lashi on 18.03.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
public class StatisticsServiceImplTest {

    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private CityService cityService;

    @Test
    public void getBiggestCountries(){
        List<Country> countries = statisticsService.getBiggestCountries(3);
        assertEquals(countryService.getCountryByCode("RUS"), countries.get(0));
        assertEquals(countryService.getCountryByCode("ATA"), countries.get(1));
        assertEquals(countryService.getCountryByCode("CAN"), countries.get(2));
    }

    @Test
    public void getMostCommonLanguages(){
        Map<String, Long> languages = statisticsService.getMostCommonLanguages(10);
        assertEquals(10, languages.size());
        assertTrue(languages.containsKey("Chinese"));
        assertEquals(Long.valueOf(1191843539), languages.get("Chinese"));
        assertTrue(languages.containsKey("Hindi"));
        assertEquals(Long.valueOf(405633070), languages.get("Hindi"));
        assertTrue(languages.containsKey("Spanish"));
        assertEquals(Long.valueOf(355029462), languages.get("Spanish"));
        assertTrue(languages.containsKey("English"));
        assertTrue(languages.containsKey("Arabic"));
        assertTrue(languages.containsKey("Bengali"));
        assertTrue(languages.containsKey("Portuguese"));
        assertTrue(languages.containsKey("Russian"));
        assertTrue(languages.containsKey("Japanese"));
        assertTrue(languages.containsKey("Punjabi"));
    }

    @Test
    public void getBiggestCities(){
        List<City> cities = statisticsService.getBiggestCities(5);
        assertEquals(cityService.getCityById(1024), cities.get(0));
        assertEquals(cityService.getCityById(2331), cities.get(1));
        assertEquals(cityService.getCityById(206), cities.get(2));
        assertEquals(cityService.getCityById(1890), cities.get(3));
        assertEquals(cityService.getCityById(939), cities.get(4));
    }

    @Test
    public void getPercentageOfUrbanPopulationOfCountry(){
        double percentage = statisticsService.getPercentageOfUrbanPopulationOfCountry("RUS");
        assertEquals(47.06, percentage, 0.01);
    }

    public void getWorldPopulation(){
        long population = statisticsService.getWorldPopulation();
        assertEquals(6078749450L, population);
    }
}