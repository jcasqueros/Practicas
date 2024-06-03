package com.viewnext.springbatchf.step.chunk.export.tramo;

import com.viewnext.springbatchf.model.entity.Street;
import com.viewnext.springbatchf.model.repository.StreetRepository;
import org.springframework.batch.item.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * The type Street item reader.
 */
@Component
public class ExportTramoReader implements ItemReader<Street>, ItemStream {

    private final StreetRepository streetRepository;
    private Iterator<Street> iterator;

    @Autowired
    public ExportTramoReader(StreetRepository streetRepository) {
        this.streetRepository = streetRepository;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        List<Street> streets = streetRepository.findAll();
        iterator = streets.iterator();
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() throws ItemStreamException {

    }

    @Override
    public Street read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }

}


