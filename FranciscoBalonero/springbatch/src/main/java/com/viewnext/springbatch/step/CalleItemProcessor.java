package com.viewnext.springbatch.step;

import com.viewnext.springbatch.model.Calle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class CalleItemProcessor implements ItemProcessor<Calle, Calle> {

    @Override
    public Calle process(Calle calle) {
        return calle;
    }

}