package com.viewnext.springbatchf.config;

import com.viewnext.springbatchf.model.TramoCalle;
import org.springframework.batch.item.ItemProcessor;

public class TramoCalleItemProcessor implements ItemProcessor<TramoCalle, TramoCalle> {

    @Override
    public TramoCalle process(final TramoCalle item) {
        return item;
    }
}