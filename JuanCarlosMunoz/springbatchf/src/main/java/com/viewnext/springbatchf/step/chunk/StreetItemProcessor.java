package com.viewnext.springbatchf.step.chunk;

import com.viewnext.springbatchf.model.entity.Street;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class StreetItemProcessor implements ItemProcessor<Street, Street> {

    private static final Logger logger = LoggerFactory.getLogger(StreetItemProcessor.class);


    @Override
    public Street process(final Street item) throws Exception {
        logger.info("Hola processor");


        return null;
    }
}
