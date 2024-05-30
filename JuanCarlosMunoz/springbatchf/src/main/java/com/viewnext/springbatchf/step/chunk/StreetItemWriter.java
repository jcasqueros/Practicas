package com.viewnext.springbatchf.step.chunk;

import com.viewnext.springbatchf.model.entity.Street;
import com.viewnext.springbatchf.model.repository.StreetRepository;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StreetItemWriter implements ItemWriter<Street> {

    @Autowired
    private StreetRepository streetRepository;


    @Override
    public void write(List<? extends Street> list) throws Exception {
        for (Street street: list) {
            streetRepository.save(street);
        }
    }
}
