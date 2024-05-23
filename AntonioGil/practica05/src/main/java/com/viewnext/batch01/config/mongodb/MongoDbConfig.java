package com.viewnext.batch01.config.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Configuration for MongoDB.
 *
 * @author Antonio Gil
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.viewnext.batch01.repository")
public class MongoDbConfig extends AbstractMongoClientConfiguration {

    @Value("${batch01.mongodb.database}")
    private String databaseName;

    @Value("${batch01.mongodb.uri}")
    private String connectionUri;

    @Bean
    public MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory databaseFactory) {
        return new MongoTransactionManager(databaseFactory);
    }

    @Bean
    public MongoTemplate mongoOperations(MongoDatabaseFactory databaseFactory) {
        return new MongoTemplate(databaseFactory);
    }

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public MongoClient mongoClient() {
        final ConnectionString connectionString = new ConnectionString(connectionUri);
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

}
