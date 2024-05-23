package com.viewnext.springbatchf.step.readers;

import com.viewnext.springbatchf.model.Calle;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that provides a MongoItemReader for reading Calle objects from a MongoDB collection.
 *
 * @author Manuel Mateos de Torres
 */
@RequiredArgsConstructor
public class MongoReader {

    private final MongoTemplate mongoTemplate;

    /**
     * Creates a MongoItemReader instance for reading Calle objects from a MongoDB collection.
     *
     * @param collection
     *         the name of the MongoDB collection to read from
     * @return a MongoItemReader instance
     */
    public MongoItemReader<Calle> createMongoItemReader(String collection) {
        MongoItemReader<Calle> reader = new MongoItemReader<>();
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("codigo", Sort.Direction.ASC);
        reader.setCollection(collection);
        reader.setTemplate(mongoTemplate);
        reader.setQuery("{}");
        reader.setSort(sorts);
        reader.setTargetType(Calle.class);
        return reader;
    }
}