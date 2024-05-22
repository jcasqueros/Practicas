package com.viewnext.springbatch.step.writer;

import com.viewnext.springbatch.model.Calle;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.data.mongodb.core.MongoTemplate;

public class CalleMongoItemWriter extends MongoItemWriter<Calle> {

    public CalleMongoItemWriter(String collection, MongoTemplate mongoTemplate) {
        setTemplate(mongoTemplate);
        setCollection(collection);
    }
}
