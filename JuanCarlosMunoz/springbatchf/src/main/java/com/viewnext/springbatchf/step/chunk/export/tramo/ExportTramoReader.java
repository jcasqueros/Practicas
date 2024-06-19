package com.viewnext.springbatchf.step.chunk.export.tramo;

import com.viewnext.springbatchf.model.entity.Street;
import com.viewnext.springbatchf.model.repository.jpa.StreetRepository;
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

    /**
     * Instantiates a new Export tramo reader.
     *
     * @param streetRepository
     *         the street repository
     */
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
        // Update not implemented because is used to update the execution context with the current state of the reader.The
        // reader doesn't need to maintain any state, so the update method can be left empty.

    }

    @Override
    public void close() throws ItemStreamException {
        // The close method is used to release any resources held by the reader.In this case, not holding any resources

    }

    @Override
    public Street read() {
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }

}


