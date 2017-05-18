package net.lashin.web.controllers;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CityControllerTest extends AbstractControllerTest {


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

//    @Test
//    public void saveCity() throws Exception {
//
//    }
//
//    @Test
//    public void editCity() throws Exception {
//
//    }
//
//    @Test
//    public void deleteCity() throws Exception {
//
//    }
//
//    @Test
//    public void filterCities() throws Exception {
//
//    }
//
//    @Test
//    public void filterCities1() throws Exception {
//
//    }

}