package com.viewnext.springbatch.config;

import com.mongodb.client.MongoClients;
import com.viewnext.springbatch.values.Values;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

/**
 * This class is responsible for configuring the MongoDB connection.
 *
 * <p>It uses the {@link org.springframework.context.annotation.Configuration} annotation to mark this class as a
 * Spring configuration class.</p>
 *
 * <p>The class is also annotated with {@link lombok.RequiredArgsConstructor} to inject the required dependencies.</p>
 *
 * @author Francisco Balonero Olivera
 */
@Configuration
@RequiredArgsConstructor
public class MongoConfig {

    /**
     * The values object that holds the application properties.
     *
     * <p>This object is injected through the constructor. // Este objeto contiene los valores de la aplicación, como
     * la URL de MongoDB</p>
     */
    private final Values values;

    /**
     * Creates a {@link org.springframework.data.mongodb.core.MongoTemplate} bean.
     *
     * <p>This bean is used to interact with the MongoDB database.
     * The MongoTemplate is created using the MongoDB URL from the application properties.</p>
     *
     * @return a new instance of MongoTemplate
     */
    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(
                new SimpleMongoClientDatabaseFactory(MongoClients.create(values.getMongoUrl()), "batch"));
    }
}

