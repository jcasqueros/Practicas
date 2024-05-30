package com.viewnext.springbatchf.step.chunk;

import com.viewnext.springbatchf.model.entity.Street;

import lombok.Getter;
import org.springframework.batch.item.ItemProcessor;

import java.util.HashMap;

@Getter
public class StreetItemProcessor implements ItemProcessor<Street, Street> {

    private static final int idDISTRICT = 3;

    private HashMap<Integer, Integer> districtCont = new HashMap<>();

    @Override
    public Street process(final Street item) throws Exception {
        int cont = 0;
        cont = districtCont.getOrDefault(item.getCodDistrito(), 0);
        districtCont.put(item.getCodDistrito(), cont + 1);

        if (item.getCodDistrito() == idDISTRICT) {
            return item;
        } else {
            return null;
        }
    }
}
