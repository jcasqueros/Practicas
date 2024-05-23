package com.viewnext.springbatch.step.writer;

import com.viewnext.springbatch.model.Calle;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * This class is responsible for writing Calle data to a MongoDB collection.
 *
 * <p>It extends Spring Batch's {@link MongoItemWriter} to write the data to a MongoDB collection.</p>
 *
 * @author Francisco Balonero Olivera
 */
public class CalleMongoItemWriter extends MongoItemWriter<Calle> {

    /**
     * Creates a new instance of CalleMongoItemWriter.
     *
     * <p>The writer is configured to write to the specified MongoDB collection using the provided MongoTemplate.</p>
     *
     * @param collection
     *         the name of the MongoDB collection to write to
     * @param mongoTemplate
     *         the MongoTemplate instance to use for writing data
     */
    public CalleMongoItemWriter(String collection, MongoTemplate mongoTemplate) {
        setTemplate(mongoTemplate);
        setCollection(collection);
    }
}
