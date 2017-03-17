package net.lashin.core.services;

import net.lashin.config.JpaConfig;
import net.lashin.config.RootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lashi on 17.03.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, JpaConfig.class})
@Transactional
public class LanguageServiceImplTest {

    @Autowired
    private LanguageService languageService;

    @Test
    public void getAllLanguages() throws Exception {

    }

    @Test
    public void getLanguagesByCountryCode() throws Exception {

    }

    @Test
    public void getLanguagesByCountryAndOfficialty() throws Exception {

    }

    @Test
    public void getLanguageByNameAndCountry() throws Exception {

    }

    @Test
    public void save() throws Exception {

    }

    @Test
    public void remove() throws Exception {

    }

}