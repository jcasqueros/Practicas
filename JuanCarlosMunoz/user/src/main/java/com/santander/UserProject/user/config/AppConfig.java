package com.santander.UserProject.user.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The type App config.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.santander.UserProject.user.config" })
public class AppConfig {

}