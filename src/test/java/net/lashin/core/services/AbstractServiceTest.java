package net.lashin.core.services;

import net.lashin.config.TestRootConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestRootConfig.class})
@Sql(scripts = {"classpath:/db/initDB.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class AbstractServiceTest {

    @Autowired
    private EhCacheCacheManager cacheManager;

    @Before
    public void setUp() {
        cacheManager.getCache("cities").clear();
        cacheManager.getCache("countries").clear();
        cacheManager.getCache("countrylanguages").clear();
    }

}
