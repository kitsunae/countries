package net.lashin.web.controllers;

import net.lashin.core.hateoas.CountryLanguageResource;
import net.lashin.util.JsonUtil;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    public void save() throws Exception {
        CountryLanguageResource resource = new CountryLanguageResource();
        resource.setLanguage("Tumba");
        resource.setCountryCode("DMA");
        resource.setOfficial(false);
        resource.setPercentage(0.01);
        resource.setDescription("Imaginary language");
        mockMvc.perform(post("/language").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(resource)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteLanguage() throws Exception {
        mockMvc.perform(delete("/language/").param("languageName", "Mordva").param("countryCode", "RUS"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}