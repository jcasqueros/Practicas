package com.viewnext.springbatchf.step.listener;

import com.viewnext.springbatchf.model.entity.District;
import com.viewnext.springbatchf.model.repository.DistrictRepository;
import com.viewnext.springbatchf.model.repository.StreetRepository;

import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class StreetImportListener implements StepExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(StreetImportListener.class);

    @Qualifier("logWriter")
    private final FileWriter logWriter;

    private final StreetRepository streetRepository;
    private final ImportStreetProcessor importStreetProcessor;
    private final DistrictRepository districtRepository;

    @Autowired
    public StreetImportListener(StreetRepository streetRepository, ImportStreetProcessor importStreetProcessor,
            DistrictRepository districtRepository, FileWriter logWriter) {

        this.streetRepository = streetRepository;
        this.importStreetProcessor = importStreetProcessor;
        this.districtRepository = districtRepository;
        this.logWriter = logWriter;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {

        logger.info("Clean MongoDB...");
        streetRepository.deleteAll();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        addDistrictToDB();
        closePointier();

        return ExitStatus.COMPLETED;
    }

    private void addDistrictToDB() {
        HashMap<String, Integer> districts = importStreetProcessor.getDistrictCont();

        for (Map.Entry<String, Integer> entry : districts.entrySet()) {
            District district = District.builder().nomDistrito(entry.getKey()).numCasas(entry.getValue()).build();
            districtRepository.save(district);
        }
    }

    private void closePointier() {
        try {
            logWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

