package net.lashin.core.services;

import net.lashin.core.beans.City;
import net.lashin.core.beans.Continent;
import net.lashin.core.beans.Country;
import net.lashin.core.filters.CountryFilter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.Assert.*;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class CountryServiceImplTest extends AbstractServiceTest {

    @Autowired
    private CountryService countryService;
    @Autowired
    private CityService cityService;

    @Test
    public void getCountriesByName() throws Exception {
        List<Country> countries = countryService.getByName("Russian Federation");
        assertEquals(1, countries.size());
        assertEquals(cityService.getById(3580), countries.get(0).getCapital());
        assertEquals("RUS", countries.get(0).getCode());
    }

    @Test
    public void getCountriesByNamePageable(){
        Page<Country> countries = countryService.getByName("Russian Federation", new PageRequest(0, 20));
        assertEquals(1, countries.getContent().size());
        assertEquals(1, countries.getTotalPages());
        assertEquals("RUS", countries.getContent().get(0).getCode());
    }

    @Test
    public void getCountryByCode() throws Exception {
        Country country = countryService.getByCode("RUS");
        assertNotNull(country);
        assertEquals("Russian Federation", country.getName());
        assertEquals(cityService.getById(3580), country.getCapital());
    }

    @Test
    public void getCountriesByCapital() throws Exception {
        List<Country> countries = countryService.getByCapital("Kingston");
        assertEquals(2, countries.size());
        assertTrue(countries.contains(countryService.getByCode("JAM")));
        assertTrue(countries.contains(countryService.getByCode("NFK")));
    }

    @Test
    public void getCountriesByCapitalNamePageable(){
        Page<Country> countries = countryService.getByCapital("Kingston", new PageRequest(0, 1));
        assertEquals(1, countries.getContent().size());
        assertEquals(2, countries.getTotalPages());
        assertEquals(countryService.getByCode("JAM"), countries.getContent().get(0));
    }

    @Test
    public void getCountriesByCapital1() throws Exception {
        List<Country> countries = countryService.getByCapital(3580L);
        assertEquals(1, countries.size());
        assertEquals(countryService.getByCode("RUS"), countries.get(0));
    }

    @Test
    public void getCountriesByCapitalCodePageable(){
        Page<Country> countries = countryService.getByCapital(3580L, new PageRequest(0, 10));
        assertEquals(1, countries.getContent().size());
        assertEquals(countryService.getByCode("RUS"), countries.getContent().get(0));
    }

    @Test
    public void getAllCountries() throws Exception {
        List<Country> countries = countryService.getAll();
        assertEquals(239, countries.size());
    }

    @Test
    public void getAllCountriesPageable(){
        Page<Country> countries = countryService.getAll(new PageRequest(0, 20));
        assertEquals(20, countries.getContent().size());
        assertEquals(239/20+1, countries.getTotalPages());
    }

    @Test
    public void getAllCountryNames() throws Exception {
        List<String> names = countryService.getAllCountryNames();
        assertEquals(239, names.size());
    }

    @Test
    public void getAllCountryNamesPageable(){
        Page<String> names = countryService.getAllCountryNames(new PageRequest(0, 20));
        assertEquals(20, names.getContent().size());
        assertEquals(239/20+1, names.getTotalPages());
    }

    @Test
    public void getCountriesByContinent() throws Exception {
        List<Country> countriesOfEurope = countryService.getByContinent(Continent.EUROPE);
        assertEquals(46, countriesOfEurope.size());
    }

    @Test
    public void getCountriesByContinentPageable(){
        Page<Country> countries = countryService.getByContinent(Continent.EUROPE, new PageRequest(0, 20));
        assertEquals(20, countries.getContent().size());
        assertEquals(3, countries.getTotalPages());
    }

    @Test
    public void getCountriesByContinentName() throws Exception {
        List<Country> countriesOfNorthAmerica = countryService.getByContinentName("North America");
        assertEquals(37, countriesOfNorthAmerica.size());
    }

    @Test
    public void getCountriesByContinentNamePageable(){
        Page<Country> countries = countryService.getByContinentName("North America", new PageRequest(0, 20));
        assertEquals(20, countries.getContent().size());
        assertEquals(2, countries.getTotalPages());
    }

    @Test
    public void getCountriesByLanguage() throws Exception {
        List<Country> countries = countryService.getByLanguage("Russian");
        assertEquals(17, countries.size());
    }

    @Test
    public void getCountriesByLanguagePageable(){
        Page<Country> countries = countryService.getByLanguage("Russian", new PageRequest(0, 5));
        assertEquals(5, countries.getContent().size());
        assertEquals(4, countries.getTotalPages());
        assertEquals(countryService.getByCode("AZE"), countries.getContent().get(0));
        assertEquals(countryService.getByCode("GEO"), countries.getContent().get(4));
    }

    @Test
    public void save() throws Exception {
        City city = new City("New Vasuki", "Chelyaba", 15);
        Country country = new Country("AAA", "Chunga-Changa", Continent.AFRICA,"Region", 0.02, 15, "Pesech", "Tyrany", "AA");
        country.setCapital(city);
        countryService.save(country);
        assertEquals(country, countryService.getByCode("AAA"));
        assertEquals(city, countryService.getByCode("AAA").getCapital());
        assertTrue(cityService.getWorldCapitals().contains(city));
    }

    @Test
    public void edit() throws Exception {
        Country country = countryService.getByCode("RUS");
        City newCapital = cityService.getByName("St Petersburg").get(0);
        country.setCapital(newCapital);
        countryService.edit(country, "RUS");
        assertTrue(cityService.getWorldCapitals().contains(newCapital));
        assertEquals(country, cityService.getById(newCapital.getId()).getCountry());
    }

    @Test
    public void remove() throws Exception {
        countryService.remove("USA");
        assertNull(countryService.getByCode("USA"));
        assertEquals(0, cityService.getByCountryCode("USA").size());
    }

    @Test
    public void remove1() throws Exception {
        Country country = countryService.getByCode("USA");
        countryService.remove(country);
        assertNull(countryService.getByCode("USA"));
        assertEquals(0, cityService.getByCountryCode("USA").size());
    }


    @Test
    public void filterCountries(){
        CountryFilter filter = new CountryFilter();
        filter.setGovernmentForm("Federal Republic");
        filter.setMinSurfaceArea(60);
        filter.setMaxSurfaceArea(10000000);
        filter.setMinIndepYear(0);
        filter.setMaxIndepYear(1990);
        filter.setMinPopulation(10000);
        filter.setMaxPopulation(1000000000);
        filter.setMinLifeExpectancy(45);
        filter.setMaxLifeExpectancy(75);
        List<Country> result = countryService.filter(filter);
        assertEquals(7, result.size());
        filter = new CountryFilter();
        filter.setMinIndepYear(0);
        filter.setMaxIndepYear(1990);
        result = countryService.filter(filter);
        assertEquals(166, result.size());
        filter = new CountryFilter();
        result = countryService.filter(filter);
        assertEquals(countryService.getAll(), result);
    }

    @Test
    public void filterCountriesPageable(){
        CountryFilter filter = new CountryFilter();
        filter.setGovernmentForm("Federal Republic");
        filter.setMinSurfaceArea(60);
        filter.setMaxSurfaceArea(10000000);
        filter.setMinIndepYear(0);
        filter.setMaxIndepYear(1990);
        filter.setMinPopulation(10000);
        filter.setMaxPopulation(1000000000);
        filter.setMinLifeExpectancy(45);
        filter.setMaxLifeExpectancy(75);
        Page<Country> result = countryService.filter(filter, new PageRequest(0, 5));
        assertEquals(5, result.getContent().size());
        assertEquals(2, result.getTotalPages());
        result = countryService.filter(filter, new PageRequest(1, 5));
        assertEquals(2, result.getContent().size());
        assertEquals(2, result.getTotalPages());
        filter = new CountryFilter();
        filter.setMinIndepYear(0);
        filter.setMaxIndepYear(1990);
        result = countryService.filter(filter, new PageRequest(3, 20));
        assertEquals(20, result.getContent().size());
        assertEquals(166/20+1, result.getTotalPages());
    }
}