package com.santander.kafkaconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Kafka consumer application.
 */
@SpringBootApplication
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
