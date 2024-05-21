package com.viewnext.batch01.step.chunk;

import com.viewnext.batch01.model.Tramo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.*;

import java.util.List;

public class TramoSetWriter implements ItemWriter<Tramo>, StepExecutionListener {

    private static final Logger outputLog =
            LoggerFactory.getLogger(TramoSetWriter.class.getCanonicalName() + "#outputLog");

    @Override
    public void beforeStep(StepExecution stepExecution) {
        // TODO: Completar método
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        // TODO: Completar método
        return ExitStatus.COMPLETED;
    }

    @Override
    public void write(List<? extends Tramo> list) {
        list.forEach(tramo -> outputLog.info("{}", tramo));
    }

}