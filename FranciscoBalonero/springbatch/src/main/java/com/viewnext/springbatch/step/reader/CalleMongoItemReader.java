package com.viewnext.springbatch.step.reader;

import com.viewnext.springbatch.model.Calle;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashMap;
import java.util.Map;

public class CalleMongoItemReader {
    public MongoItemReader<Calle> mongoItemReader(String collectionName, MongoTemplate mongoTemplate) {
        Map<String, Sort.Direction> distritoCounts = new HashMap<>();
        distritoCounts.put("_id", Sort.Direction.ASC);
        MongoItemReader<Calle> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setCollection(collectionName);
        reader.setSort(distritoCounts);
        reader.setQuery("{}");
        reader.setTargetType(Calle.class);
        return reader;
    }
}
