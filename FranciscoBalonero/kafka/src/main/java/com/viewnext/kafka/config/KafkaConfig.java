package com.viewnext.kafka.config;

import com.viewnext.kafka.model.Message;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for Kafka settings.
 *
 * <p>This class provides configuration settings for Kafka, including the creation of a {@link KafkaTemplate}
 * bean. The {@link KafkaTemplate} bean is used for sending messages to Kafka topics.</p>
 *
 * <p>The configuration settings are loaded from the application properties file, specifically the
 * {@code spring.kafka.bootstrap-servers} property.</p>
 *
 * <p>Instances of this class are typically used as a configuration source for the Spring framework, defining beans and
 * their dependencies.</p>
 *
 * @author Francisco Balonero Olivera
 * @see org.springframework.kafka.core.KafkaTemplate
 * @see org.apache.kafka.clients.producer.ProducerConfig
 */
@Configuration
public class KafkaConfig {
    /**
     * The bootstrap servers for Kafka, loaded from the application properties file.
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Creates a KafkaTemplate bean.
     *
     * @return {@link KafkaTemplate} The KafkaTemplate bean
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs()));
    }

    /**
     * Creates a map of producer configurations.
     *
     * @return {@link Map} The producer configurations
     */
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }
}
