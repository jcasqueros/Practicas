package com.viewnext.springbatchf.job;

import com.mongodb.client.MongoClients;
import lombok.Data;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Data
public class MongoDataSource {

    public MongoDataSource(String url) {
        new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(url), "batch"));
    }
}

