package net.lashin.core.services;

import net.lashin.config.RootConfig;
import net.lashin.core.beans.CountryLanguage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void getLanguagesByCountryAndOfficialty() throws Exception {
        List<CountryLanguage> list = languageService.getLanguagesByCountryAndOfficialty("RUS", true);
        assertEquals(1, list.size());
        assertEquals("Russian", list.get(0).getLanguage());
        assertTrue(list.get(0).isOfficial());
        assertEquals(countryService.getCountryByCode("RUS"), list.get(0).getCountry());
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
}