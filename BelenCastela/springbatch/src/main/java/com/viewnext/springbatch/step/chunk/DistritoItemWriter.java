package com.viewnext.springbatch.step.chunk;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.viewnext.springbatch.model.Direccion;
import com.viewnext.springbatch.model.Distrito;
import com.viewnext.springbatch.service.DistritoService;

public class DistritoItemWriter implements ItemWriter<Direccion> {

    @Autowired
    private DistritoService distritoService;
    
    @Autowired
    private DistritoCounterItemProcessor distritoCounterItemProcessor;

    @Override
    public void write(Chunk<? extends Direccion> items) throws Exception {
        Map<String, Integer> distritoCountMap = distritoCounterItemProcessor.getDistritoCountMap();
        List<Distrito> distritos = distritoCountMap.entrySet().stream()
                .map(entry -> new Distrito(null, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        distritoService.saveAll(distritos);
    }
}

/*public class DistritoItemWriter implements ItemWriter<Map<String, Integer>> {

    @Autowired
    private DistritoService distritoService;

    @Override
    public void write(Chunk<? extends Map<String, Integer>> items) throws Exception {
        for (Map<String, Integer> distritoCountMap : items) {
            List<Distrito> distritos = distritoCountMap.entrySet().stream()
                    .map(entry -> new Distrito(null, entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
            distritoService.saveAll(distritos);
        }
    }
}*/