package com.viewnext.batch01.step;

import com.viewnext.batch01.model.Tramo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Scope;

@Scope(value = "step")
public class TramoItemProcessor implements ItemProcessor<Tramo, Tramo> {

    private static final Logger log = LoggerFactory.getLogger(TramoItemProcessor.class.getCanonicalName());
    private static final Logger acceptedLineLog =
            LoggerFactory.getLogger(TramoItemProcessor.class.getCanonicalName() + "#acceptedLineLogger");

    private final String districtName;

    public TramoItemProcessor() {
        this.districtName = "";
    }

    public TramoItemProcessor(String districtName) {
        this.districtName = districtName;
    }

    @Override
    public Tramo process(Tramo tramo) {
        if (districtName.equals(tramo.getNombreDistrito())) {
            log.info("Tramo aceptado: {}", tramo);
            acceptedLineLog.info("{}", tramo);

            return tramo;
        }

        return null;
    }

}
