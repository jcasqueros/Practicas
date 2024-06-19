package com.viewnext.springbatchf.step.chunk.export.district;

import com.viewnext.springbatchf.model.entity.District;
import com.viewnext.springbatchf.model.repository.jpa.DistrictRepository;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * The type Street item reader.
 */
@Component
public class ExportDistrictReader implements ItemReader<District>, ItemStream {

    private final DistrictRepository districtRepository;
    private Iterator<District> iterator;

    /**
     * Instantiates a new Export district reader.
     *
     * @param districtRepository
     *         the district repository
     */
    @Autowired
    public ExportDistrictReader(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        List<District> streets = districtRepository.findAll();
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
    public District read() {
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }

}


