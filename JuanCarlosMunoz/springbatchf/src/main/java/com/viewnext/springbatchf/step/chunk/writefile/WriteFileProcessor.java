package com.viewnext.springbatchf.step.chunk.writefile;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * The type Write file processor.
 */
@Component
public class WriteFileProcessor implements ItemProcessor<Object, Object> {
    @Override
    public Object process(Object o) {
        return o;
    }
}
