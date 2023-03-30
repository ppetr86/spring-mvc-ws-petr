package com.shopapp.batch.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

//doesnt work now because of flyway...
@Profile("dev")
@Component
public class BatchConfig {

    //managiging different schemas
    @Bean
    @Primary
    //@ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/shop_app_monolith")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .username("shop_app_monolith_user")
                .password("shop_app_monolith_user")
                .build();
       /* return DataSourceBuilder.create()
                .build();*/
    }

    /*@Bean
    //@ConfigurationProperties(prefix = "spring.testschema")
    public DataSource testSchemaDataSource(){
       *//* return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/test")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .username("root")
                .password("root")
                .build();*//*
        return DataSourceBuilder.create().build();
    }*/
}
