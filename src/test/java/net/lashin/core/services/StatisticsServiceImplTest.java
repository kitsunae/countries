package net.lashin.core.services;

import net.lashin.config.RootConfig;
import net.lashin.core.beans.City;
import net.lashin.core.beans.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        assertEquals(countryService.getByCode("RUS"), countries.get(0));
        assertEquals(countryService.getByCode("ATA"), countries.get(1));
        assertEquals(countryService.getByCode("CAN"), countries.get(2));
    }

    @Test
    public void getBiggestCountriesPageable(){
        Page<Country> countries = statisticsService.getBiggestCountries(new PageRequest(0, 10));
        assertEquals(10, countries.getContent().size());
        assertEquals(239/10+1, countries.getTotalPages());
        assertEquals(countryService.getByCode("RUS"), countries.getContent().get(0));
        assertEquals(countryService.getByCode("ATA"), countries.getContent().get(1));
        assertEquals(countryService.getByCode("CAN"), countries.getContent().get(2));
        assertEquals(countryService.getByCode("KAZ"), countries.getContent().get(9));
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
    public void getMostCommonLanguagesPageable(){
        Page<Map<String, Long>> languages = statisticsService.getMostCommonLanguages(new PageRequest(0,10));
        assertEquals(10, languages.getContent().size());
        assertEquals(457/10+1, languages.getTotalPages());
        assertTrue(languages.getContent().get(0).containsKey("Chinese"));
        assertEquals(Long.valueOf(1191843539), languages.getContent().get(0).get("Chinese"));
        assertTrue(languages.getContent().get(1).containsKey("Hindi"));
        assertEquals(Long.valueOf(405633070), languages.getContent().get(1).get("Hindi"));
        assertTrue(languages.getContent().get(2).containsKey("Spanish"));
        assertEquals(Long.valueOf(355029462), languages.getContent().get(2).get("Spanish"));
        assertTrue(languages.getContent().get(3).containsKey("English"));
        assertTrue(languages.getContent().get(4).containsKey("Arabic"));
        assertTrue(languages.getContent().get(5).containsKey("Bengali"));
        assertTrue(languages.getContent().get(6).containsKey("Portuguese"));
        assertTrue(languages.getContent().get(7).containsKey("Russian"));
        assertTrue(languages.getContent().get(8).containsKey("Japanese"));
        assertTrue(languages.getContent().get(9).containsKey("Punjabi"));
    }

    @Test
    public void getBiggestCities(){
        List<City> cities = statisticsService.getBiggestCities(5);
        assertEquals(cityService.getById(1024), cities.get(0));
        assertEquals(cityService.getById(2331), cities.get(1));
        assertEquals(cityService.getById(206), cities.get(2));
        assertEquals(cityService.getById(1890), cities.get(3));
        assertEquals(cityService.getById(939), cities.get(4));
    }

    @Test
    public void getBiggestCitiesPageable(){
        Page<City> cities = statisticsService.getBiggestCities(new PageRequest(0, 5));
        assertEquals(5, cities.getContent().size());
        assertEquals(4079/5+1, cities.getTotalPages());
        assertEquals(cityService.getById(1024), cities.getContent().get(0));
        assertEquals(cityService.getById(2331), cities.getContent().get(1));
        assertEquals(cityService.getById(206), cities.getContent().get(2));
        assertEquals(cityService.getById(1890), cities.getContent().get(3));
        assertEquals(cityService.getById(939), cities.getContent().get(4));
    }

    @Test
    public void getPercentageOfUrbanPopulationOfCountry(){
        double percentage = statisticsService.getPercentageOfUrbanPopulationOfCountry("RUS");
        assertEquals(47.06, percentage, 0.01);
    }

    @Test
    public void getWorldPopulation(){
        long population = statisticsService.getWorldPopulation();
        assertEquals(6078749450L, population);
    }

}