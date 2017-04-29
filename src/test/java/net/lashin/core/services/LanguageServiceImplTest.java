package net.lashin.core.services;

import net.lashin.config.TestRootConfig;
import net.lashin.core.beans.Continent;
import net.lashin.core.beans.CountryLanguage;
import net.lashin.core.filters.LanguageFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestRootConfig.class})
@Transactional
@Sql(scripts = {"classpath:/db/initDB.sql"})
public class LanguageServiceImplTest {

    @Autowired
    private LanguageService languageService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private EhCacheCacheManager cacheManager;

    @Test
    public void getAllLanguages() throws Exception {
        List<String> languages = languageService.getAllLanguageNames();
        assertEquals(457, languages.size());
        List<CountryLanguage> countryLanguages = languageService.getAll();
        assertEquals(984, countryLanguages.size());
    }

    @Test
    public void getAllLanguagesPageable(){
        Page<String> languages = languageService.getAllLanguageNames(new PageRequest(1, 20));
        assertEquals(20, languages.getContent().size());
        assertEquals(457/20+1, languages.getTotalPages());
    }

    @Test
    public void getAllCountryLanguagesPageable(){
        Page<CountryLanguage> languages = languageService.getAll(new PageRequest(0, 20));
        assertEquals(20, languages.getContent().size());
        assertEquals(984/20+1, languages.getTotalPages());
    }

    @Test
    public void getLanguagesByCountryCode() throws Exception {
        List<CountryLanguage> languages = languageService.getByCountryCode("RUS");
        assertEquals(12, languages.size());
        List<String> names = languages.stream()
                .map(CountryLanguage::getLanguage)
                .collect(Collectors.toList());
        assertTrue(names.contains("Russian"));
        assertTrue(names.contains("Avarian"));
        assertEquals(countryService.getByCode("RUS"), languages.get(0).getCountry());
    }

    @Test
    public void getLanguagesByCountryCodePageable(){
        Page<CountryLanguage> languages = languageService.getByCountryCode("RUS", new PageRequest(0, 5));
        assertEquals(5, languages.getContent().size());
        assertEquals(3, languages.getTotalPages());
        assertEquals("Avarian", languages.getContent().get(0).getLanguage());
        assertEquals("Chuvash", languages.getContent().get(4).getLanguage());
    }

    @Test
    public void getLanguagesByCountryAndOfficialty() throws Exception {
        cacheManager.getCache("countrylanguages").clear();
        List<CountryLanguage> list = languageService.getByCountryAndOfficialty("RUS", true);
        assertEquals(1, list.size());
        assertEquals("Russian", list.get(0).getLanguage());
        assertTrue(list.get(0).isOfficial());
        assertEquals(countryService.getByCode("RUS"), list.get(0).getCountry());
    }

    @Test
    public void getLanguagesByCountryAndOfficialtyPageable(){
        Page<CountryLanguage> list = languageService.getByCountryAndOfficialty("RUS", false, new PageRequest(1, 5));
        assertEquals(5, list.getContent().size());
        assertEquals(3, list.getTotalPages());
        assertEquals("Kazakh", list.getContent().get(0).getLanguage());
        assertEquals("Udmur", list.getContent().get(4).getLanguage());
    }

    @Test
    public void getLanguageByNameAndCountry() throws Exception {
        CountryLanguage language = languageService.getByNameAndCountry("Tatar", "RUS");
        assertEquals("Tatar", language.getLanguage());
        assertEquals(3.2, language.getPercentage(), 0.001);
        assertFalse(language.isOfficial());
        assertEquals(countryService.getByCode("RUS"), language.getCountry());
    }

    @Test
    @Rollback
    public void save() throws Exception {
        CountryLanguage language = new CountryLanguage("RUS", "OLOLOLO", true, 99.0);
        languageService.save(language);
        assertTrue(languageService.getByCountryAndOfficialty("RUS", true).contains(language));
        assertTrue(languageService.getByCountryCode("RUS").contains(language));
    }

    @Test
    @Rollback
    public void remove() throws Exception {
        languageService.remove("English", "USA");
        assertTrue(languageService.getByCountryAndOfficialty("USA", true).isEmpty());
        assertFalse(languageService.getByCountryCode("USA")
                .stream()
                .map(CountryLanguage::getLanguage)
                .collect(Collectors.toList()).contains("English"));
    }

    @Test
    @Rollback
    public void editTest() throws Exception{
        CountryLanguage language = languageService.getByNameAndCountry("Chinese", "USA");
        language.setPercentage(45.3);
        language.setOfficial(true);
        languageService.save(language);
        assertEquals(language, languageService.getByNameAndCountry("Chinese", "USA"));
        assertEquals(2, languageService.getByCountryAndOfficialty("USA", true).size());
        assertEquals(language.getPercentage(), languageService.getByNameAndCountry("Chinese", "USA").getPercentage(), 0.001);
    }

    @Test
    public void filterLanguages(){
        LanguageFilter filter = new LanguageFilter();
        filter.setContinent(Continent.EUROPE);
        filter.setRegion("Eastern Europe");
        filter.setMinPercentage(50.0);
        List<CountryLanguage> languages = languageService.filter(filter);
        assertEquals(10, languages.size());
    }

    @Test
    public void filterLanguagesPageable(){
        LanguageFilter filter = new LanguageFilter();
        filter.setContinent(Continent.EUROPE);
        filter.setRegion("Eastern Europe");
        filter.setMinPercentage(50.0);
        Page<CountryLanguage> languages = languageService.filter(filter, new PageRequest(1, 5));
        assertEquals(5, languages.getContent().size());
        assertEquals(2, languages.getTotalPages());
        assertEquals("Polish", languages.getContent().get(0).getLanguage());
        assertEquals("Ukrainian", languages.getContent().get(4).getLanguage());
    }
}