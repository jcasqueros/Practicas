package com.viewnext.springbatchf.step.listener;

import com.opencsv.CSVWriter;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TramoExportListener implements StepExecutionListener {

    @Qualifier("tramoCSVWriter")
    private final CSVWriter tramoCSVWriter;

    @Autowired
    public TramoExportListener(CSVWriter tramoCSVWriter) {
        this.tramoCSVWriter = tramoCSVWriter;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

        String[] tramoHeader = { "BARRIO", "COD_DISTRITO", "CODIGO_CALLE", "NOM_DISTRITO", "NOMBRE_CALLE",
                "PRIMER_NUM_TRAMO", "TIPO_VIA", "ULTIMO_NUM_TRAMO" };

        tramoCSVWriter.writeNext(tramoHeader,false);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        try {
            tramoCSVWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ExitStatus.COMPLETED;
    }
}
