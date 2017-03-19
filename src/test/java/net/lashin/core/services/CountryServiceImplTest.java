package net.lashin.core.services;

import net.lashin.config.RootConfig;
import net.lashin.core.beans.City;
import net.lashin.core.beans.Continent;
import net.lashin.core.beans.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lashi on 17.03.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Transactional
public class CountryServiceImplTest {

    @Autowired
    private CountryService countryService;
    @Autowired
    private CityService cityService;

    @Test
    public void getCountriesByName() throws Exception {
        List<Country> countries = countryService.getCountriesByName("Russian Federation");
        assertEquals(1, countries.size());
        assertEquals(cityService.getCityById(3580), countries.get(0).getCapital());
        assertEquals("RUS", countries.get(0).getCode());
    }

    @Test
    public void getCountryByCode() throws Exception {
        Country country = countryService.getCountryByCode("RUS");
        assertNotNull(country);
        assertEquals("Russian Federation", country.getName());
        assertEquals(cityService.getCityById(3580), country.getCapital());
    }

    @Test
    public void getCountriesByCapital() throws Exception {
        List<Country> countries = countryService.getCountriesByCapital("Kingston");
        assertEquals(2, countries.size());
        assertTrue(countries.contains(countryService.getCountryByCode("JAM")));
        assertTrue(countries.contains(countryService.getCountryByCode("NFK")));
    }

    @Test
    public void getCountriesByCapital1() throws Exception {
        List<Country> countries = countryService.getCountriesByCapital(3580L);
        assertEquals(1, countries.size());
        assertEquals(countryService.getCountryByCode("RUS"), countries.get(0));
    }

    @Test
    public void getAllCountries() throws Exception {
        List<Country> countries = countryService.getAllCountries();
        assertEquals(239, countries.size());
    }

    @Test
    public void getAllCountryNames() throws Exception {
        List<String> names = countryService.getAllCountryNames();
        assertEquals(239, names.size());
    }

    @Test
    public void getCountriesByContinent() throws Exception {
        List<Country> countriesOfEurope = countryService.getCountriesByContinent(Continent.EUROPE);
        assertEquals(46, countriesOfEurope.size());
    }

    @Test
    public void getCountriesByContinentName() throws Exception {
        List<Country> countriesOfNorthAmerica = countryService.getCountriesByContinentName("North America");
        assertEquals(37, countriesOfNorthAmerica.size());
    }

    @Test
    public void getCountriesByLanguage() throws Exception {
        List<Country> countries = countryService.getCountriesByLanguage("Russian");
        assertEquals(17, countries.size());
    }

    @Test
    @Rollback
    public void save() throws Exception {
        City city = new City("New Vasuki", "Chelyaba", 15);
        Country country = new Country("AAA", "Chunga-Changa", Continent.AFRICA,"Region", 0.02, 15, "Pesech", "Tyrany", "AA");
        country.addCity(city);
        country.setCapital(city);
        countryService.save(country);
        assertEquals(country, countryService.getCountryByCode("AAA"));
        assertEquals(city, countryService.getCountryByCode("AAA").getCapital());
        assertTrue(cityService.getWorldCapitals().contains(city));
    }

    @Test
    @Rollback
    public void edit() throws Exception {
        Country country = countryService.getCountryByCode("RUS");
        City newCapital = cityService.getCitiesByName("St Petersburg").get(0);
        country.setCapital(newCapital);
        countryService.save(country);
        assertEquals(newCapital, countryService.getCountriesByCapital(newCapital.getId()).get(0).getCapital());
        assertTrue(cityService.getWorldCapitals().contains(newCapital));
        assertEquals(country, cityService.getCityById(newCapital.getId()).getCountry());
    }

    @Test
    @Rollback
    public void remove() throws Exception {
        countryService.remove("USA");
        assertNull(countryService.getCountryByCode("USA"));
        assertEquals(0, cityService.getCitiesByCountryCode("USA").size());
    }

    @Test
    @Rollback
    public void remove1() throws Exception {
        Country country = countryService.getCountryByCode("USA");
        countryService.remove(country);
        assertNull(countryService.getCountryByCode("USA"));
        assertEquals(0, cityService.getCitiesByCountryCode("USA").size());
    }

}