package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.model.Calle;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.data.mongodb.core.MongoTemplate;

@RequiredArgsConstructor
public class DistritoWriter1 extends MongoItemWriter<Calle> {

    public DistritoWriter1(MongoTemplate mongoTemplate) {
        setTemplate(mongoTemplate);
        setCollection("distritoEspecifico");
    }
}
