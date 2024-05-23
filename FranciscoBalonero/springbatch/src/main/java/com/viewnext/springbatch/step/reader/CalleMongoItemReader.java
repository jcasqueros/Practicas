package com.viewnext.springbatch.step.reader;

import com.viewnext.springbatch.model.Calle;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for reading Calle data from a MongoDB collection.
 *
 * <p>It uses Spring Batch's {@link MongoItemReader} to read the data from the MongoDB collection and map it to a
 * {@link Calle} object.</p>
 *
 * @author Francisco Balonero Olivera
 */
public class CalleMongoItemReader {

    /**
     * Creates a {@link MongoItemReader} instance to read Calle data from a MongoDB collection.
     *
     * <p>The reader is configured to read from the specified collection, sort the data by barrio in ascending order,
     * and map the data to a {@link Calle} object.</p>
     *
     * @param collectionName
     *         the name of the MongoDB collection to read from
     * @param mongoTemplate
     *         the MongoTemplate instance to use for reading data
     * @return a new instance of {@link MongoItemReader} configured to read Calle data
     */
    public MongoItemReader<Calle> mongoItemReader(String collectionName, MongoTemplate mongoTemplate) {
        Map<String, Sort.Direction> distritoCounts = new HashMap<>();
        distritoCounts.put("barrio", Sort.Direction.ASC);
        MongoItemReader<Calle> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection(collectionName);
        reader.setSort(distritoCounts);
        reader.setQuery("{}");
        reader.setTargetType(Calle.class);
        return reader;
    }
}
