package net.lashin.web.controllers;

import net.lashin.core.beans.Continent;
import net.lashin.core.beans.Country;
import net.lashin.core.hateoas.CountryResource;
import net.lashin.core.hateoas.asm.ResourceHandler;
import net.lashin.core.services.CountryService;
import net.lashin.util.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class CountryControllerTest extends AbstractControllerTest {

    @Autowired
    private CountryService service;
    @Autowired
    private ResourceHandler<Country, CountryResource> resourceHandler;

    @Test
    public void getCountry() throws Exception {
        mockMvc.perform(get("/country/RUS"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getAllCountries() throws Exception {
        mockMvc.perform(get("/country/butch/all"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getAllCountries1() throws Exception {
        mockMvc.perform(get("/country/all").param("page", "3").param("size", "5"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getAllCountryNames() throws Exception {
        mockMvc.perform(get("/country/names"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getAllCountryNames1() throws Exception {
        mockMvc.perform(get("/country/butch/names"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getCountriesByContinent() throws Exception {
        mockMvc.perform(get("/country/continent/Europe"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getCountriesByContinent1() throws Exception {
        mockMvc.perform(get("/country/butch/continent/Europe"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getCountriesWithSameLanguage() throws Exception {
        mockMvc.perform(get("/country/language/Russian").param("page", "0").param("size", "5"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getCountriesWithSameLanguage1() throws Exception {
        mockMvc.perform(get("/country/butch/language/Russian"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getCountriesByCapital() throws Exception {
        mockMvc.perform(get("/country/capital/Kingston"))
                .andDo(print());
    }

    @Test
    public void getCountriesByCapital1() throws Exception {
        mockMvc.perform(get("/country/butch/capital/Kingston"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void save() throws Exception {
        CountryResource countryResource = new CountryResource();
        countryResource.setCode("AAA");
        countryResource.setName("Chunga-Changa");
        countryResource.setLocalName("Chu-Cha");
        countryResource.setCode2("AA");
        countryResource.setIndepYear(1990);
        CountryResource.Demography demography = new CountryResource.Demography();
        demography.setPopulation(1000);
        demography.setLifeExpectancy(80.2);
        countryResource.setDemography(demography);
        CountryResource.Geography geography = new CountryResource.Geography();
        geography.setContinent(Continent.AFRICA.toString());
        geography.setSurfaceArea(1300d);
        geography.setRegion("Eastern Africa");
        countryResource.setGeography(geography);
        CountryResource.Economy economy = new CountryResource.Economy();
        economy.setGnp(12d);
        economy.setGnpOld(11d);
        countryResource.setEconomy(economy);
        CountryResource.Policy policy = new CountryResource.Policy();
        policy.setGovernmentForm("Monarchy");
        policy.setHeadOfState("Otello");
        countryResource.setPolicy(policy);
        /*
    "code": "AAA",
    "name": "Chunga-Changa",
    "indepYear": 1990,
    "localName": "Chu-Cha",
    "code2": "AA",
    "capital": null,
    "geography": {
        "continent": "Africa",
        "surfaceArea": 1300,
        "region": "Eastern Africa"
    },
    "demography": {
        "population": 1000,
        "lifeExpectancy": 80.2
    },
    "economy": {
        "gnp": 12,
        "gnpOld": 11
    },
    "policy": {
        "governmentForm": "Monarchy",
        "headOfState": "Otello"
    },
    "description": null
    */
        mockMvc.perform(post("/country").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(countryResource)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    public void edit() throws Exception {
        Country country = service.getByCode("RUS");
        country.setDescription("Biggest country in the world");
        CountryResource countryResource = resourceHandler.toResource(country);
        mockMvc.perform(put("/country/RUS").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(countryResource)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/country/USA"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}