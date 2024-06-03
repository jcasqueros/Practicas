package com.viewnext.springbatchf.step.chunk.export.tramo;

import com.opencsv.CSVWriter;
import com.viewnext.springbatchf.model.entity.Street;
import lombok.Getter;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
public class ExportTramoWriter implements ItemWriter<Street> {

    @Qualifier("tramoCSVWriter")
    private final CSVWriter tramoCSVWriter;

    @Autowired
    public ExportTramoWriter(CSVWriter tramoCSVWriter) {
        this.tramoCSVWriter = tramoCSVWriter;

    }

    @Override
    public void write(List<? extends Street> list) throws Exception {

        //TODO: Agregar al afterStep

        //            //Escribimos las cabeceras en los archivos
        //            String[] data = { "BARRIO", "COD_DISTRITO", "CODIGO_CALLE", "NOM_DISTRITO", "NOMBRE_CALLE",
        //                    "PRIMER_NUM_TRAMO", "TIPO_VIA", "ULTIMO_NUM_TRAMO" };
        //
        //            writer.writeNext(data);

        for (Street street : list) {

            //Escribimos en el fichero los distritos
            String[] dataTramo = { street.getBarrio(), String.valueOf(street.getCodDistrito()), street.getCodigoCalle(),
                    street.getNomDistrito(), street.getNombreCalle(), String.valueOf(street.getPrimerNumeroTramo()),
                    street.getTipoVia(), String.valueOf(street.getUltimoNumeroTramo()) };
            tramoCSVWriter.writeNext(dataTramo, false);

        }
    }

}



