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
public class DistrictExportListener implements StepExecutionListener {



    @Qualifier("districtCSVWriter")
    private final CSVWriter districtCSVWriter;

    @Autowired
    public DistrictExportListener( CSVWriter districtCSVWriter) {
        this.districtCSVWriter = districtCSVWriter;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        String[] districtHeader = { "NOM_DISTRITO", "NUM_CASAS" };

        districtCSVWriter.writeNext(districtHeader,false);

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        try {
            districtCSVWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ExitStatus.COMPLETED;
    }
}
