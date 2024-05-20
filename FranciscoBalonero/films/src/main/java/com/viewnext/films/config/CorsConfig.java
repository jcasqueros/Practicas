package com.viewnext.films.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for CORS (Cross-Origin Resource Sharing) settings.
 *
 * <p>This class enables CORS support for the application, allowing requests from different origins.</p>
 *
 * @author Francisco Balonero Olivera
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Configures CORS mappings for the application.
     *
     * @param registry
     *         the CORS registry to configure
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
    }
}
