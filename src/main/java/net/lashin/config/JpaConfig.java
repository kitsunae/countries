package net.lashin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by lashi on 09.02.2017.
 */
@Configuration
@EnableJpaRepositories(basePackages = "net.lashin.dao")
public class JpaConfig {
}
