package net.lashin.services;

import net.lashin.beans.City;
import net.lashin.beans.Continent;
import net.lashin.beans.Country;
import net.lashin.beans.CountryLanguage;
import net.lashin.config.JpaConfig;
import net.lashin.config.RootConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by lashi on 15.02.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, JpaConfig.class})
public class WorldServiceTest {

    @Autowired
    private WorldService service;

    private Country country;
    private City capital;
    private CountryLanguage language;

    @Before
    @Transactional
    public void setUp(){
        this.country = new Country("AAA", "Limpopo", Continent.AFRICA, "Chunga-Changa",
                100.01d, 1990, 1234, 99.9, 100.1, 90.2, "Limpo", "Anarchy", "Ai-Bolit", "AA");
        this.capital = new City("Chunge", "Change", 111);
        this.language = new CountryLanguage(true, 99.9, country, "Nigra");
        country.setCapital(capital);
        service.save(country);
    }

    @Test
    @Transactional
    public void addingTest(){
        assertEquals(this.capital, service.getCityByName(this.capital.getName()));
        assertEquals(this.language, service.getLanguagesByCountry(this.country.getCode()).get(0));
        this.country = service.getCountryByCapital(capital);
        City city = new City("hehe", "haha", 100, this.country);
        service.save(city);
        assertTrue(service.getCountryByName(this.country.getName()).getCities().contains(capital));
        assertTrue(service.getCountryByName(this.country.getName()).getCities().contains(city));
        Country country = new Country("AAA", "Limpopo", Continent.AFRICA, "Chunga-Changa",
                100.01d, 1990, 1260, 99.9, 100.1, 90.2, "Limpo", "Anarchy", "Ai-Bolit", "AA");
        country.setCapital(this.capital);
        service.save(country);
        assertNotNull(service.getCountryByName(country.getName()).getCities());
        assertNotEquals(0, service.getCountryByName(country.getName()).getCities());
        assertNotNull(service.getCountryByName(country.getName()).getLanguages());
        assertNotEquals(0, service.getCountryByName(country.getName()).getLanguages().size());
        assertNotNull(service.getCityByName(city.getName()));
        assertTrue(service.getCountryByCode(country.getCode()).getCities().contains(city));
        assertEquals(country, service.getCityByName(city.getName()).getCountry());
        service.remove(country);
        assertNull(service.getCountryByName(country.getName()));
        assertNull(service.getCityByName(this.capital.getName()));
        assertNull(service.getCityByName(city.getName()));
        assertEquals(0, service.getLanguagesByCountry(country.getCode()).size());
    }

    @Test
    @Transactional
    public void removalTest(){
        Country country = new Country("AAA", "Limpopo", Continent.AFRICA, "Chunga-Changa",
                100.01d, 1990, 1234, 99.9, 100.1, 90.2, "Limpo", "Anarchy", "Ai-Bolit", "AA");
        service.remove(country);
        assertNull(service.getCountryByCode(this.country.getCode()));
        assertNull(service.getCityByName(this.capital.getName()));
        assertNull(service.getLanguageByNameAndCountry("Nigra", this.country.getCode()));
    }
}