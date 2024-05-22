package com.viewnext.springbatch.step;

import com.viewnext.springbatch.model.Calle;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Item writer that writes Calle objects to a MongoDB collection.
 *
 * @author Francisco Balonero Olivera
 * @see MongoItemWriter
 */
public class CalleItemWriter extends MongoItemWriter<Calle> {

    /**
     * Creates a new instance of CalleItemWriter that writes to a MongoDB collection.
     *
     * @param mongoTemplate
     *         the MongoTemplate instance to use for writing
     */
    public CalleItemWriter(MongoTemplate mongoTemplate) {
        setTemplate(mongoTemplate);
        setCollection("calles");
    }
}
