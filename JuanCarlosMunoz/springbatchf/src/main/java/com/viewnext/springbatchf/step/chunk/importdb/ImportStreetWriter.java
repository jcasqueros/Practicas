package com.viewnext.springbatchf.step.chunk.importdb;

import com.viewnext.springbatchf.model.entity.Street;
import com.viewnext.springbatchf.model.repository.jpa.StreetRepository;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * The type Import street writer.
 */
public class ImportStreetWriter implements ItemWriter<Street> {

    @Autowired
    private StreetRepository streetRepository;

    @Override
    public void write(List<? extends Street> list) {
        for (Street street : list) {
            streetRepository.save(street);
        }
    }
}
