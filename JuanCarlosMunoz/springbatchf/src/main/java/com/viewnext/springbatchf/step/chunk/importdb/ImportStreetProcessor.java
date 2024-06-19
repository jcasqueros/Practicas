package com.viewnext.springbatchf.step.chunk.importdb;

import com.viewnext.springbatchf.model.entity.Street;

import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Objects;

/**
 * The type Import street processor.
 */
@Getter
@Setter
public class ImportStreetProcessor implements ItemProcessor<Street, Street> {

    private final HashMap<String, Integer> districtCont = new HashMap<>();

    @Value("${filter}")
    private String filter;

    @Override
    public Street process(final Street item) {
        int cont = districtCont.getOrDefault(item.getNomDistrito(), 0);
        districtCont.put(item.getNomDistrito(), cont + 1);

        if (Objects.equals(item.getNomDistrito(), filter) || Objects.equals("ALL", filter) ) {
            return item;
        } else {
            return null;
        }
    }
}
