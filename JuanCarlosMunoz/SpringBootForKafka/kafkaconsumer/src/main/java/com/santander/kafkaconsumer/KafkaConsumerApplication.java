package com.santander.kafkaconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The type Kafka consumer application.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.santander.kafkaconsumer.config")
public class KafkaConsumerApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args
	 *         the input arguments
	 */
	public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerApplication.class, args);
    }

}
