package com.shopapp;

import com.shopapp.security.AppProperties;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot REST API Documentation",
                description = "Spring Boot REST API Documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Petr",
                        email = "Novotny",
                        url = "https://www.placeholder.net"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.placeholder.net"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring Boot Webshop Documentation",
                url = "https://www.placeholder.net"
        )
)
public class SpringmvcWsPetrApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringmvcWsPetrApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "AppProperties")
    public AppProperties getAppProperties() {
        return new AppProperties();
    }

    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }

}
