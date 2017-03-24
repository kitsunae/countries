package net.lashin.core.services;

import net.lashin.config.RootConfig;
import net.lashin.core.beans.Continent;
import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.filters.LanguageFilter;
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

/**
 * Created by lashi on 17.03.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class})
@Transactional
public class LanguageServiceImplTest {

    @Autowired
    private LanguageService languageService;
    @Autowired
    private CountryService countryService;

    @Test
    public void getAllLanguages() throws Exception {
        List<String> languages = languageService.getAllLanguageNames();
        assertEquals(457, languages.size());
        List<CountryLanguage> countryLanguages = languageService.getAllLanguages();
        assertEquals(984, countryLanguages.size());
    }

    @Test
    public void getAllLanguagesPageable(){
        Page<String> languages = languageService.getAllLanguageNames(new PageRequest(1, 20));
        assertEquals(20, languages.getContent().size());
        assertEquals(457/20+1, languages.getTotalPages());
        assertEquals("Macedonian", languages.getContent().get(0));
        assertEquals("Polish", languages.getContent().get(19));
    }

    @Test
    public void getAllCountryLanguagesPageable(){
        Page<CountryLanguage> languages = languageService.getAllLanguages(new PageRequest(0,20));
        assertEquals(20, languages.getContent().size());
        assertEquals(984/20+1, languages.getTotalPages());
    }

    @Test
    public void getLanguagesByCountryCode() throws Exception {
        List<CountryLanguage> languages = languageService.getLanguagesByCountryCode("RUS");
        assertEquals(12, languages.size());
        List<String> names = languages.stream()
                .map(CountryLanguage::getLanguage)
                .collect(Collectors.toList());
        assertTrue(names.contains("Russian"));
        assertTrue(names.contains("Avarian"));
        assertEquals(countryService.getCountryByCode("RUS"), languages.get(0).getCountry());
    }

    @Test
    public void getLanguagesByCountryCodePageable(){
        Page<CountryLanguage> languages = languageService.getLanguagesByCountryCode("RUS", new PageRequest(0,5));
        assertEquals(5, languages.getContent().size());
        assertEquals(3, languages.getTotalPages());
        assertEquals("Avarian", languages.getContent().get(0).getLanguage());
        assertEquals("Chuvash", languages.getContent().get(4).getLanguage());
    }

    @Test
    public void getLanguagesByCountryAndOfficialty() throws Exception {
        List<CountryLanguage> list = languageService.getLanguagesByCountryAndOfficialty("RUS", true);
        assertEquals(1, list.size());
        assertEquals("Russian", list.get(0).getLanguage());
        assertTrue(list.get(0).isOfficial());
        assertEquals(countryService.getCountryByCode("RUS"), list.get(0).getCountry());
    }

    @Test
    public void getLanguagesByCountryAndOfficialtyPageable(){
        Page<CountryLanguage> list = languageService.getLanguagesByCountryAndOfficialty("RUS", false, new PageRequest(1, 5));
        assertEquals(5, list.getContent().size());
        assertEquals(3, list.getTotalPages());
        assertEquals("Kazakh", list.getContent().get(0).getLanguage());
        assertEquals("Udmur", list.getContent().get(4).getLanguage());
    }

    @Test
    public void getLanguageByNameAndCountry() throws Exception {
        CountryLanguage language = languageService.getLanguageByNameAndCountry("Tatar", "RUS");
        assertEquals("Tatar", language.getLanguage());
        assertEquals(Double.valueOf(3.2), language.getPercentage());
        assertFalse(language.isOfficial());
        assertEquals(countryService.getCountryByCode("RUS"), language.getCountry());
    }

    @Test
    @Rollback
    public void save() throws Exception {
        CountryLanguage language = new CountryLanguage("RUS", "OLOLOLO", true, 99.0);
        languageService.save(language);
        assertTrue(languageService.getLanguagesByCountryAndOfficialty("RUS", true).contains(language));
        assertTrue(languageService.getLanguagesByCountryCode("RUS").contains(language));
    }

    @Test
    @Rollback
    public void remove() throws Exception {
        languageService.remove("English", "USA");
        assertTrue(languageService.getLanguagesByCountryAndOfficialty("USA", true).isEmpty());
        assertFalse(languageService.getLanguagesByCountryCode("USA")
                .stream()
                .map(CountryLanguage::getLanguage)
                .collect(Collectors.toList()).contains("English"));
    }

    @Test
    @Rollback
    public void editTest() throws Exception{
        CountryLanguage language = languageService.getLanguageByNameAndCountry("Chinese", "USA");
        language.setPercentage(45.3);
        language.setOfficial(true);
        languageService.save(language);
        assertEquals(language, languageService.getLanguageByNameAndCountry("Chinese", "USA"));
        assertEquals(2, languageService.getLanguagesByCountryAndOfficialty("USA", true).size());
        assertEquals(language.getPercentage(), languageService.getLanguageByNameAndCountry("Chinese", "USA").getPercentage());
    }

    @Test
    public void filterLanguages(){
        LanguageFilter filter = new LanguageFilter();
        filter.setContinent(Continent.EUROPE);
        filter.setRegion("Eastern Europe");
        filter.setMinPercentage(50.0);
        List<CountryLanguage> languages = languageService.filterLanguages(filter);
        assertEquals(10, languages.size());
    }

    @Test
    public void filterLanguagesPageable(){
        LanguageFilter filter = new LanguageFilter();
        filter.setContinent(Continent.EUROPE);
        filter.setRegion("Eastern Europe");
        filter.setMinPercentage(50.0);
        Page<CountryLanguage> languages = languageService.filterLanguages(filter, new PageRequest(1,5));
        assertEquals(5, languages.getContent().size());
        assertEquals(2, languages.getTotalPages());
        assertEquals("Polish", languages.getContent().get(0).getLanguage());
        assertEquals("Ukrainian", languages.getContent().get(4).getLanguage());
    }
}