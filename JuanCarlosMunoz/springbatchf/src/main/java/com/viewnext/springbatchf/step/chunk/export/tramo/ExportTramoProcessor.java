package com.viewnext.springbatchf.step.chunk.export.tramo;

import com.viewnext.springbatchf.model.entity.Street;
import lombok.Getter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * The type Export item processor.
 */
@Getter
@Component
public class ExportTramoProcessor implements ItemProcessor<Street, Street> {

    private final HashMap<Integer, Integer> districtCont = new HashMap<>();

    @Override
    public Street process(final Street item) {

        return item;

    }
}
