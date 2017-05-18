package net.lashin.web.controllers;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class LanguageControllerTest extends AbstractControllerTest {

    @Test
    public void getLanguagesByCountry() throws Exception {
        mockMvc.perform(get("/language/butch/country/RUS"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getLanguagesByCountry1() throws Exception {
        mockMvc.perform(get("/language/country/RUS").param("page", "0").param("size", "5"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getOfficialLanguagesOfCountry() throws Exception {
        mockMvc.perform(get("/language/butch/official/RUS"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getOfficialLanguagesOfCountry1() throws Exception {
        mockMvc.perform(get("/language/official/RUS").param("page", "0").param("size", "5"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getUnofficialLanguagesOfCountry() throws Exception {
        mockMvc.perform(get("/language/butch/unofficial/RUS"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getUnofficialLanguagesOfCountry1() throws Exception {
        mockMvc.perform(get("/language/unofficial/RUS").param("page", "0").param("size", "5"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllLanguages() throws Exception {
        mockMvc.perform(get("/language/butch/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllLanguages1() throws Exception {
        mockMvc.perform(get("/language/all").param("page", "0").param("size", "5"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getLanguage() throws Exception {
        mockMvc.perform(get("/language/").param("languageName", "Russian").param("countryCode", "RUS"))
                .andDo(print())
                .andExpect(status().isOk());
    }

//    @Test
//    public void save() throws Exception {
//
//    }
//
//    @Test
//    public void delete() throws Exception {
//
//    }
//
//    @Test
//    public void filterLanguages() throws Exception {
//
//    }
//
//    @Test
//    public void filterLanguages1() throws Exception {
//
//    }

}