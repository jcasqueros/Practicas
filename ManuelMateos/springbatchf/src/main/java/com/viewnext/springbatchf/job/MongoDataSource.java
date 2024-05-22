package com.viewnext.springbatchf.job;

import com.mongodb.client.MongoClients;
import lombok.Data;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Data
public class MongoDataSource {

    private MongoTemplate mongoTemplate;

    public MongoDataSource() {
        mongoTemplate = new MongoTemplate(
                new SimpleMongoClientDatabaseFactory(MongoClients.create("mongodb://localhost:27017"), "batch"));
    }
}

