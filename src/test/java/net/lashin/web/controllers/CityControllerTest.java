package net.lashin.web.controllers;

import net.lashin.core.beans.City;
import net.lashin.core.hateoas.CityResource;
import net.lashin.core.hateoas.asm.ResourceHandler;
import net.lashin.core.services.CityService;
import net.lashin.util.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class CityControllerTest extends AbstractControllerTest {

    @Autowired
    private CityService service;
    @Autowired
    private ResourceHandler<City, CityResource> resourceHandler;

    @Test
    public void getCity() throws Exception {
        mockMvc.perform(get("/city/3580"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getAllCities() throws Exception {
        mockMvc.perform(get("/city/butch/all"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getAllCities1() throws Exception {
        mockMvc.perform(get("/city/all").param("page", "0").param("size", "5"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getAllCitiesOfCountry() throws Exception {
        mockMvc.perform(get("/city/butch/all/RUS"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getAllCitiesOfCountry1() throws Exception {
        mockMvc.perform(get("/city/all/RUS").param("page", "1").param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCapitals() throws Exception {
        mockMvc.perform(get("/city/butch/capitals"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCapitals1() throws Exception {
        mockMvc.perform(get("/city/capitals").param("page", "0").param("size", "20"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getCitiesByName() throws Exception {
        mockMvc.perform(get("/city/butch/find").param("name", "Hamilton"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getCitiesByName1() throws Exception {
        mockMvc.perform(get("/city/butch/find").param("name", "Hamilton").param("page", "0").param("size", "2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void saveCity() throws Exception {
        /*
        {
            "name": "Tiranda",
            "countryCode": "USA",
            "district": "Turanda",
            "population": 100,
            "description": null
        }
        */
        CityResource resource = new CityResource();
        resource.setName("Tiranda");
        resource.setCountryCode("USA");
        resource.setDistrict("Turanda");
        resource.setPopulation(100);
        mockMvc.perform(post("/city").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(resource)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void editCity() throws Exception {
        City city = service.getById(3580);
        city.setDescription("Capital of Russia");
        CityResource cityResource = resourceHandler.toResource(city);
        mockMvc.perform(put("/city/3580").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(cityResource)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCity() throws Exception {
        mockMvc.perform(delete("/city/3813"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}