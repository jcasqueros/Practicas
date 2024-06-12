package com.santander.kafkaprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Kafka provider application.
 */
@SpringBootApplication
public class KafkaProviderApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args
	 *         the input arguments
	 */
	public static void main(String[] args) {
        SpringApplication.run(KafkaProviderApplication.class, args);

    }

}
