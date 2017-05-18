package net.lashin.web.controllers;

import net.lashin.config.TestRootConfig;
import net.lashin.config.WebConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, TestRootConfig.class}, loader = AnnotationConfigWebContextLoader.class)
@Sql(scripts = {"classpath:/db/initDB.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class AbstractControllerTest {

    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private EhCacheCacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        cacheManager.getCache("cities").clear();
        cacheManager.getCache("countries").clear();
        cacheManager.getCache("countrylanguages").clear();
    }
}
