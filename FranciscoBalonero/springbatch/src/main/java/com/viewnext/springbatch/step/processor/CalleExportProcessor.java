package com.viewnext.springbatch.step.processor;

import com.viewnext.springbatch.model.Calle;
import org.springframework.batch.item.ItemProcessor;

public class CalleExportProcessor implements ItemProcessor<Calle, Calle> {
    @Override
    public Calle process(Calle calle) throws Exception {
        return calle;
    }
}
