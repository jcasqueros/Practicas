package com.viewnext.springbatch.config;

import com.mongodb.client.MongoClients;
import com.viewnext.springbatch.values.Values;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
@RequiredArgsConstructor
public class MongoConfig {

    private final Values values;

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(
                new SimpleMongoClientDatabaseFactory(MongoClients.create(values.getMongoUrl()), "batch"));
    }
}

