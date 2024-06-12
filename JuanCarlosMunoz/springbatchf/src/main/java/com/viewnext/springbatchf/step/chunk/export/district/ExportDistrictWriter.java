package com.viewnext.springbatchf.step.chunk.export.district;

import com.opencsv.CSVWriter;
import com.viewnext.springbatchf.model.entity.District;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * The type Export district writer.
 */
@Component
@Getter
public class ExportDistrictWriter implements ItemWriter<District> {
    /**
     * The Districts.
     */
    HashMap<String, Integer> districts;

    @Qualifier("districtCSVWriter")
    private final CSVWriter districtCSVWriter;

    private static final Logger logger = LoggerFactory.getLogger(ExportDistrictWriter.class);

    /**
     * Instantiates a new Export district writer.
     *
     * @param districtCSVWriter
     *         the district csv writer
     */
    @Autowired
    public ExportDistrictWriter(CSVWriter districtCSVWriter) {
        this.districtCSVWriter = districtCSVWriter;

    }

    @Override
    public void write(List<? extends District> list) {

        for (District district : list) {

            //Escribimos en el fichero los distritos
            String[] dataDistrito = { district.getNomDistrito(), String.valueOf(district.getNumCasas()) };

            districtCSVWriter.writeNext(dataDistrito, false);
        }

    }

}
