package com.viewnext.springbatch.config;

import com.mongodb.client.MongoClients;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

/**
 * Configuration class for MongoDB connection.
 *
 * @author Francisco Balonero Olivera
 * @see MongoTemplate
 */
@AllArgsConstructor
public class MongoConfig {

    /**
     * MongoDB URL.
     */
    private String mongoUrl;

    /**
     * Creates a new instance of {@link MongoTemplate} using the provided MongoDB URL.
     *
     * @return a new instance of {@link MongoTemplate}
     */
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUrl), "batch"));
    }
}

