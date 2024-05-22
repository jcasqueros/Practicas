package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.model.Calle;
import org.springframework.batch.item.data.MongoPagingItemReader;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.HashMap;

public class MongoReader {

    private MongoTemplate mongoTemplate;

    public MongoPagingItemReader<Calle> reader() {
        MongoPagingItemReader<Calle> reader = new MongoPagingItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setQuery(new Query(Criteria.where("distritos").exists(true)));
        reader.setSort(new HashMap<String, Sort.Direction>() {{
            put("_id", Sort.Direction.ASC);
        }});
        reader.setPageSize(100); // tamaño de página para la paginación
        return reader;
    }
}
