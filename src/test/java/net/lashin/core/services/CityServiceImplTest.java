package net.lashin.core.services;

import net.lashin.config.TestRootConfig;
import net.lashin.core.beans.City;
import net.lashin.core.beans.Continent;
import net.lashin.core.beans.Country;
import net.lashin.core.filters.CityFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestRootConfig.class})
@Transactional
public class CityServiceImplTest {

    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;

    @Test
    public void getCitiesByName() throws Exception {
        List<City> cities = cityService.getByName("Hamilton");
        assertEquals(3, cities.size());
        List<Country> countries = cities.stream().map(City::getCountry).collect(Collectors.toList());
        for (Country country: countries){
            assertEquals("Elisabeth II", country.getPolicy().getHeadOfState());
        }
    }

    @Test
    public void getCitiesByNamePageable(){
        Page<City> cities = cityService.getByName("Hamilton", new PageRequest(1, 1));
        assertEquals(1, cities.getContent().size());
        assertEquals(3, cities.getTotalPages());
        assertEquals(cityService.getById(1821L), cities.getContent().get(0));
    }

    @Test
    public void getWorldCapitals() throws Exception {
        List<City> capitals = cityService.getWorldCapitals();
        assertEquals(232, capitals.size());
        assertTrue(capitals.contains(cityService.getById(3580)));
        for (City capital : capitals){
            assertTrue(countryService.getByCapital(capital.getId()).contains(capital.getCountry()));
        }
    }

    @Test
    public void getWorldCapitalsPageable(){
        Page<City> capitals = cityService.getWorldCapitals(new PageRequest(0, 20));
        assertEquals(20, capitals.getContent().size());
        assertEquals(232/20+1, capitals.getTotalPages());
    }

    @Test
    public void getAllCities() throws Exception {
        List<City> cities = cityService.getAll();
        assertEquals(4079, cities.size());
    }

    @Test
    public void getAllCitiesPageable(){
        Page<City> cities = cityService.getAll(new PageRequest(1, 20));
        assertEquals(20, cities.getContent().size());
        assertEquals(4079/20+1, cities.getTotalPages());
        assertEquals(cityService.getById(21L), cities.getContent().get(0));
        assertEquals(cityService.getById(40L), cities.getContent().get(19));
    }

    @Test
    @Rollback
    public void edit() throws Exception {
        City city = cityService.getById(3580);
        city.setName("New Moscow");
        cityService.edit(city, 3580L, "RUS");
        assertEquals(city, cityService.getById(3580));
    }

    @Test
    @Rollback
    public void save() throws Exception {
        Country country = countryService.getByCode("USA");
        City city = new City("Foolishing", "Dumb AC", 7_000_000, country);
        cityService.save(city, country.getCode());
        assertTrue(cityService.getByCountry(country).contains(city));
    }

    @Test
    public void getCitiesByCountryCode() throws Exception {
        List<City> cities = cityService.getByCountryCode("RUS");
        assertEquals(189, cities.size());
        Country country = countryService.getByCode("RUS");
        for (City city : cities){
            assertEquals(country, city.getCountry());
        }
    }

    @Test
    public void getCitiesByCountryCodePageable(){
        Page<City> cities = cityService.getByCountryCode("RUS", new PageRequest(3, 20));
        assertEquals(20, cities.getContent().size());
        assertEquals(189/20+1, cities.getTotalPages());
        assertEquals(cityService.getById(3640), cities.getContent().get(0));
        assertEquals(cityService.getById(3659), cities.getContent().get(19));
    }

    @Test
    public void getCitiesByCountry() throws Exception {
        Country country = countryService.getByCode("RUS");
        List<City> cities = cityService.getByCountry(country);
        assertEquals(189, cities.size());
        for (City city : cities){
            assertEquals(country, city.getCountry());
        }
    }

    @Test
    public void getCitiesByCountryPageable(){
        Country country = countryService.getByCode("RUS");
        Page<City> cities = cityService.getByCountry(country, new PageRequest(3, 20));
        assertEquals(20, cities.getContent().size());
        assertEquals(189/20+1, cities.getTotalPages());
        assertEquals(cityService.getById(3640), cities.getContent().get(0));
        assertEquals(cityService.getById(3659), cities.getContent().get(19));
    }

    @Test
    public void getCityById() throws Exception {
        City city = cityService.getById(3580);
        assertEquals("Moscow", city.getName());
        assertEquals(8389200, city.getPopulation());
        assertEquals("Moscow (City)", city.getDistrict());
        assertEquals(countryService.getByCode("RUS"), city.getCountry());
    }

    @Test
    @Rollback
    public void remove() throws Exception {
        City city = cityService.getById(3580);
        cityService.remove(3580L);
        assertNull(countryService.getByCode("RUS").getCapital());
        assertFalse(cityService.getByCountryCode("RUS").contains(city));
        assertNull(cityService.getById(3580));
    }


    @Test
    public void filterCities(){
        CityFilter filter = new CityFilter();
        filter.setRegion("Eastern Europe");
        List<City> result = cityService.filter(filter);
        assertEquals(371, result.size());
        filter = new CityFilter();
        filter.setCountry("RUS");
        filter.setMinPopulation(1000000);
        result = cityService.filter(filter);
        assertEquals(12, result.size());
        assertEquals(cityService.getById(3580), result.get(0));
        filter = new CityFilter();
        filter.setContinent(Continent.AFRICA);
        filter.setMinPopulation(1000000);
        result = cityService.filter(filter);
        assertEquals(23, result.size());
        filter = new CityFilter();
        filter.setMinPopulation(5000000);
        result = cityService.filter(filter);
        assertEquals(24, result.size());
    }

    @Test
    public void filterCitiesPageable(){
        CityFilter filter = new CityFilter();
        filter.setRegion("Eastern Europe");
        Page<City> result = cityService.filter(filter, new PageRequest(2, 20));
        assertEquals(20, result.getContent().size());
        assertEquals(371/20+1, result.getTotalPages());
        assertEquals(cityService.getById(2954), result.getContent().get(0));
        assertEquals(cityService.getById(3019), result.getContent().get(19));
    }
}