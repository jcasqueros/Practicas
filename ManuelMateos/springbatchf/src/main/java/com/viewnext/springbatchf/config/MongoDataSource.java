package com.viewnext.springbatchf.config;

import com.mongodb.client.MongoClients;
import lombok.Data;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

/**
 * Class that provides a data source for MongoDB.
 *
 * @author Manuel Mateos de Torres
 */
@Data
public class MongoDataSource {

    /**
     * MongoDB template used to interact with the database.
     */
    private MongoTemplate mongoTemplate;

    /**
     * Constructor that initializes the data source with a MongoDB connection URL.
     *
     * @param url
     *         MongoDB connection URL.
     */
    public MongoDataSource(String url) {
        new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(url), "batch"));
    }
}