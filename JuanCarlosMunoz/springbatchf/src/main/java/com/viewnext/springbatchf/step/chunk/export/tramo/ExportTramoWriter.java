package com.viewnext.springbatchf.step.chunk.export.tramo;

import com.opencsv.CSVWriter;
import com.viewnext.springbatchf.model.entity.Street;
import lombok.Getter;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Export tramo writer.
 */
@Component
@Getter
public class ExportTramoWriter implements ItemWriter<Street> {

    @Qualifier("tramoCSVWriter")
    private final CSVWriter tramoCSVWriter;

    /**
     * Instantiates a new Export tramo writer.
     *
     * @param tramoCSVWriter
     *         the tramo csv writer
     */
    @Autowired
    public ExportTramoWriter(CSVWriter tramoCSVWriter) {
        this.tramoCSVWriter = tramoCSVWriter;

    }

    @Override
    public void write(List<? extends Street> list) {

        for (Street street : list) {

            //Escribimos en el fichero los distritos
            String[] dataTramo = { street.getBarrio(), String.valueOf(street.getCodDistrito()), street.getCodigoCalle(),
                    street.getNomDistrito(), street.getNombreCalle(), String.valueOf(street.getPrimerNumeroTramo()),
                    street.getTipoVia(), String.valueOf(street.getUltimoNumeroTramo()) };
            tramoCSVWriter.writeNext(dataTramo, false);

        }
    }

}



