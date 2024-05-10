package com.santander.peliculacrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The type Application.
 */
@SpringBootApplication

public class Application {

	/**
	 * The entry point of application.
	 *
	 * @param args
	 *         the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
