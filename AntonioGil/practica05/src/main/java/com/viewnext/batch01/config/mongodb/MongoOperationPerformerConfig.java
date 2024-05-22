package com.viewnext.batch01.config.mongodb;

import com.viewnext.batch01.model.Tramo;
import com.viewnext.batch01.model.history.DistrictFilterHistoryEntry;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.text.MessageFormat;
import java.time.Instant;

/**
 * Configuration for MongoDB operation performers (readers and writers).
 *
 * @author Antonio Gil
 */
@Configuration
public class MongoOperationPerformerConfig {

    @Bean
    @StepScope
    public MongoItemWriter<Tramo> districtFilterWriter(MongoTemplate mongoTemplate,
                                                       @Value("#{jobParameters['batch01.district']}") String districtName) {
        final long timestamp = Instant.now().toEpochMilli();
        final String collectionName = MessageFormat.format("district_filter-{0}-{1,number,#}", districtName,
                timestamp);

        MongoItemWriter<Tramo> writer = new MongoItemWriter<>();
        writer.setCollection(collectionName);
        writer.setTemplate(mongoTemplate);

        return writer;
    }

    @Bean
    public MongoItemWriter<DistrictFilterHistoryEntry> districtFilterAfterJobItemWriter(MongoTemplate mongoTemplate) {
        MongoItemWriter<DistrictFilterHistoryEntry> writer = new MongoItemWriter<>();
        writer.setCollection("_history");
        writer.setTemplate(mongoTemplate);

        return writer;
    }

}
