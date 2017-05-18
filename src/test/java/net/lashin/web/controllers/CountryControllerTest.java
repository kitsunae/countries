package net.lashin.web.controllers;

import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class CountryControllerTest extends AbstractControllerTest {


    @Test
    public void getCountry() throws Exception {
        mockMvc.perform(get("/country/RUS"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void getAllCountries() throws Exception {
        mockMvc.perform(get("/country/butch/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void getAllCountries1() throws Exception {
        mockMvc.perform(get("/country/all").param("page", "3").param("size", "5"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void getAllCountryNames() throws Exception {
        mockMvc.perform(get("/country/names"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void getAllCountryNames1() throws Exception {
        mockMvc.perform(get("/country/butch/names"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void getCountriesByContinent() throws Exception {
        mockMvc.perform(get("/country/continent/Europe"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void getCountriesByContinent1() throws Exception {
        mockMvc.perform(get("/country/butch/continent/Europe"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void getCountriesWithSameLanguage() throws Exception {
        mockMvc.perform(get("/country/language/Russian").param("page", "0").param("size", "5"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void getCountriesWithSameLanguage1() throws Exception {
        mockMvc.perform(get("/country/butch/language/Russian"))
                .andExpect(MockMvcResultMatchers.status().isOk())
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
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

//    @Test
//    public void save() throws Exception {
//
//    }
//
//    @Test
//    public void edit() throws Exception {
//
//    }
//
//    @Test
//    public void delete() throws Exception {
//
//    }

}