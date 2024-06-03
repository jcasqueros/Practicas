package com.viewnext.springbatchf.step.chunk.export.district;

import com.viewnext.springbatchf.model.entity.District;
import lombok.Getter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * The type Export item processor.
 */
@Getter
@Component
public class ExportDistrictProcessor implements ItemProcessor<District, District> {

    private final HashMap<Integer, Integer> districtCont = new HashMap<>();

    @Override
    public District process(final District item) {

        return item;

    }
}
