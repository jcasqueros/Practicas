package com.viewnext.springbatchf.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.data.mongodb.core.MongoTemplate;

@RequiredArgsConstructor
public class DistritoWriter extends MongoItemWriter {

    public DistritoWriter(MongoTemplate mongoTemplate) {
        setTemplate(mongoTemplate);
        setCollection("distritoEspecifico");
    }
}
