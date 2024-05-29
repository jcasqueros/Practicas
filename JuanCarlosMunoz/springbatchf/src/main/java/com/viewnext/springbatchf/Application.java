package com.viewnext.springbatchf;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableBatchProcessing
public class Application {

	public static void main(String[] args) {
		SpringApplication.exit(new SpringApplicationBuilder(Application.class)
				.web(WebApplicationType.NONE)
				.run(args));
	}

}
