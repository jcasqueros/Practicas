package com.viewnext.kafkademo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuration for the application.
 *
 * @author Antonio Gil
 */
@Configuration
public class KafkaDemoAppConfig {

    @Value("${kafka-demo.kafka.topic-name}")
    private String topicName;

    @Value("${kafka-demo.kafka.partition-count}")
    private int partitionCount;

    @Value("${kafka-demo.kafka.replica-count}")
    private int replicaCount;

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topicName)
                .partitions(partitionCount)
                .replicas(replicaCount)
                .build();
    }

}
