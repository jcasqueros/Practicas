package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.model.Calle;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

@RequiredArgsConstructor
public class DistritoCountWriter extends MongoItemWriter {

    public DistritoCountWriter(MongoTemplate mongoTemplate) {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("distrito"),
                Aggregation.count().as("count"));
        AggregationResults<Calle> results = mongoTemplate.aggregate(aggregation, "calles", Calle.class);
        mongoTemplate.insert(results, "distritos_count");
    }
}
