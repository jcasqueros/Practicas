package com.viewnext.bsan.practica04.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Additional configuration for unit tests. It takes care of pointing to some packages that Spring's component scan
 * mechanisms fail to find by default.
 *
 * @author Antonio Gil
 */
@TestConfiguration
@ComponentScan(basePackages = "com.viewnext.bsan.practica04.repository.custom")
public class CrudPeliculasAppTestConfig {
}
