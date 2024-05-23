package com.viewnext.springbatchf.step.writers;

import com.viewnext.springbatchf.model.Calle;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * A class that extends MongoItemWriter to write Calle objects to a MongoDB collection.
 *
 * @author Manuel Mateos de Torres
 */
@RequiredArgsConstructor
public class MongoWriter extends MongoItemWriter<Calle> {

    /**
     * Creates a MongoWriter instance to write Calle objects to a MongoDB collection.
     *
     * @param mongoTemplate
     *         the MongoTemplate instance
     * @param collection
     *         the name of the MongoDB collection
     */
    public MongoWriter(MongoTemplate mongoTemplate, String collection) {
        setTemplate(mongoTemplate);
        setCollection(collection);
    }
}
