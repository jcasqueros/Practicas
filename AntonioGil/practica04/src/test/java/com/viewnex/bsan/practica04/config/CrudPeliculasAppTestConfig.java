package com.viewnex.bsan.practica04.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = "com.viewnex.bsan.practica04.repository.custom")
public class CrudPeliculasAppTestConfig {
}
