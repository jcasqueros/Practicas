package com.viewnext.springbatchf.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.viewnext.springbatchf.model.repository")
@ComponentScan(basePackages = "com.viewnext.springbatchf.model.repository")
public class MongoConfig {
}

