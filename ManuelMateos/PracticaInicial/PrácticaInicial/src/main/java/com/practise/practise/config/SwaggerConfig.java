package com.practise.practise.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Practise Spring Boot").version("1.0")
                .contact(new Contact().name("Manuel Mateos de Torres").url("https://github.com/Manumdt"))
                .license(new License().name("License of API").url("API license URL")));
    }
}
