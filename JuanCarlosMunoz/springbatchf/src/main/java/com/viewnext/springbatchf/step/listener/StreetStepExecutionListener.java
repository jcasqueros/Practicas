package com.viewnext.springbatchf.step.listener;

import com.viewnext.springbatchf.model.repository.StreetRepository;

import com.viewnext.springbatchf.step.chunk.StreetItemProcessor;
import com.viewnext.springbatchf.step.skippolicy.StreetSkipPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StreetStepExecutionListener implements StepExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(StreetStepExecutionListener.class);

    private final StreetRepository streetRepository;
    private final StreetItemProcessor  streetItemProcessor;


    @Autowired
    public StreetStepExecutionListener(StreetRepository streetRepository, StreetItemProcessor streetItemProcessor){

        this.streetRepository =  streetRepository;
        this.streetItemProcessor = streetItemProcessor;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {

        logger.info("Clean MongoDB...");
        streetRepository.deleteAll();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        HashMap<Integer, Integer> districtCont = streetItemProcessor.getDistrictCont();

        for(Map.Entry<Integer, Integer> entry : districtCont.entrySet()) {
            logger.info("District with code {} appears {} times", entry.getKey(), entry.getValue());

        }

        return ExitStatus.COMPLETED;
    }

}

