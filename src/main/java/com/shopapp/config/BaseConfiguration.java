package com.shopapp.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
class BaseConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BaseConfiguration.class);

    @Bean
    @Profile("dev")
    void dev() {
        System.out.println("Currently active profile is: dev");
    }

    @Bean
    @Profile("local")
    void local() {
        System.out.println("Currently active profile is: local");
    }

    @PostConstruct
    void postConstruct() {
        logger.info("loaded BaseConfiguration!");
    }

}
