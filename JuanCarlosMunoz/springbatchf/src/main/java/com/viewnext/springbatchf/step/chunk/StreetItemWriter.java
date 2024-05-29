package com.viewnext.springbatchf.step.chunk;

import com.viewnext.springbatchf.model.entity.Street;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import java.util.List;

public class StreetItemWriter implements ItemWriter<Street> {


    private static final Logger logger = LoggerFactory.getLogger(StreetItemWriter.class);

    @Override
    public void write(List<? extends Street> list) throws Exception {
        logger.info("Hola writer");

    }
}
