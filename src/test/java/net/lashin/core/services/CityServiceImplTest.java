package net.lashin.core.services;

import net.lashin.config.RootConfig;
import net.lashin.core.beans.City;
import net.lashin.core.beans.Country;
import net.lashin.core.filters.CityFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by lashi on 17.03.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Transactional
public class CityServiceImplTest {

    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;

    @Test
    public void getCitiesByName() throws Exception {
        List<City> cities = cityService.getCitiesByName("Hamilton");
        assertEquals(3, cities.size());
        List<Country> countries = cities.stream().map(City::getCountry).collect(Collectors.toList());
        for (Country country: countries){
            assertEquals("Elisabeth II", country.getHeadOfState());
        }
    }

    @Test
    public void getWorldCapitals() throws Exception {
        List<City> capitals = cityService.getWorldCapitals();
        assertEquals(232, capitals.size());
        assertTrue(capitals.contains(cityService.getCityById(3580)));
        for (City capital : capitals){
            assertTrue(countryService.getCountriesByCapital(capital.getId()).contains(capital.getCountry()));
        }
    }

    @Test
    public void getAllCities() throws Exception {
        List<City> cities = cityService.getAllCities();
        assertEquals(4079, cities.size());
    }

    @Test
    @Rollback
    public void edit() throws Exception {
        City city = cityService.getCityById(3580);
        city.setName("New Moscow");
        cityService.edit(city, 3580L, "RUS");
        assertEquals(city, cityService.getCityById(3580));
    }

    @Test
    @Rollback
    public void save() throws Exception {
        Country country = countryService.getCountryByCode("USA");
        City city = new City("Foolishing", "Dumb AC", 7_000_000, country);
        cityService.save(city, country.getCode());
        assertTrue(cityService.getCitiesByCountry(country).contains(city));
    }

    @Test
    public void getCitiesByCountryCode() throws Exception {
        List<City> cities = cityService.getCitiesByCountryCode("RUS");
        assertEquals(189, cities.size());
        Country country = countryService.getCountryByCode("RUS");
        for (City city : cities){
            assertEquals(country, city.getCountry());
        }
    }

    @Test
    public void getCitiesByCountry() throws Exception {
        Country country = countryService.getCountryByCode("RUS");
        List<City> cities = cityService.getCitiesByCountry(country);
        assertEquals(189, cities.size());
        for (City city : cities){
            assertEquals(country, city.getCountry());
        }
    }

    @Test
    public void getCityById() throws Exception {
        City city = cityService.getCityById(3580);
        assertEquals("Moscow", city.getName());
        assertEquals(Integer.valueOf(8389200), city.getPopulation());
        assertEquals("Moscow (City)", city.getDistrict());
        assertEquals(countryService.getCountryByCode("RUS"), city.getCountry());
    }

    @Test
    @Rollback
    public void remove() throws Exception {
        City city = cityService.getCityById(3580);
        cityService.remove(3580L);
        assertNull(countryService.getCountryByCode("RUS").getCapital());
        assertFalse(cityService.getCitiesByCountryCode("RUS").contains(city));
        assertNull(cityService.getCityById(3580));
    }


    @Test
    public void filterCities(){
        CityFilter filter = new CityFilter();
        filter.setRegion("Eastern Europe");
        List<City> result = cityService.filterCities(filter);
        assertEquals(371, result.size());
    }
}