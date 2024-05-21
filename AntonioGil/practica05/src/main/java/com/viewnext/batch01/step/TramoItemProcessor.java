package com.viewnext.batch01.step;

import com.viewnext.batch01.model.Tramo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class TramoItemProcessor implements ItemProcessor<Tramo, Tramo> {

    private static final Logger log = LoggerFactory.getLogger(TramoItemProcessor.class.getCanonicalName());

    @Override
    public Tramo process(Tramo tramo) {
        log.debug("Tramo recibido: {}", tramo);
        return tramo;
    }

}
