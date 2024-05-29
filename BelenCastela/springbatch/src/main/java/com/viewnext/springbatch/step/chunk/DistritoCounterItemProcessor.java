package com.viewnext.springbatch.step.chunk;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.viewnext.springbatch.model.Direccion;
import com.viewnext.springbatch.model.Distrito;
import com.viewnext.springbatch.service.DistritoService;

public class DistritoCounterItemProcessor implements ItemProcessor<Direccion, Direccion> {

    private Map<String, Integer> distritoCountMap = new HashMap<>();

    @Override
    public Direccion process(Direccion item) throws Exception {
        distritoCountMap.put(item.getNomDistrito(), distritoCountMap.getOrDefault(item.getNomDistrito(), 0) + 1);
        return item;
    }

    public Map<String, Integer> getDistritoCountMap() {
        return distritoCountMap;
    }
}

/*public class DistritoCounterItemProcessor implements ItemProcessor<Direccion, Map<String, Integer>> {

    private Map<String, Integer> distritoCountMap = new HashMap<>();

    @Override
    public Map<String, Integer> process(Direccion item) throws Exception {
        distritoCountMap.put(item.getNomDistrito(), distritoCountMap.getOrDefault(item.getNomDistrito(), 0) + 1);
        return distritoCountMap;
    }

    public Map<String, Integer> getDistritoCountMap() {
        return distritoCountMap;
    }
}
*/