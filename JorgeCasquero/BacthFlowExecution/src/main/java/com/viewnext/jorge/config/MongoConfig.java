package com.viewnext.jorge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import com.mongodb.client.MongoClients;
import com.viewnext.jorge.values.Values;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MongoConfig {
	private final Values values;

	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(
				new SimpleMongoClientDatabaseFactory(MongoClients.create(values.getMongoUrl()), "batch"));
	}
}
