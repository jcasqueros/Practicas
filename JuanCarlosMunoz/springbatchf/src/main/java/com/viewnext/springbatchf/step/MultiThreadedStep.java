package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.model.entity.Street;
import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetReader;
import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetWriter;
import com.viewnext.springbatchf.step.listener.StreetImportListener;
import com.viewnext.springbatchf.step.skippolicy.StreetSkipPolicy;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;

/**
 * The type Multi threaded step.
 */
public class MultiThreadedStep {

    /**
     * Import step.
     *
     * @param reader
     *         the reader
     * @param writer
     *         the writer
     * @param stepBuilderFactory
     *         the step builder factory
     * @param streetImportListener
     *         the street import listener
     * @param streetSkipPolicy
     *         the street skip policy
     * @param resource
     *         the resource
     * @param taskExecutor
     *         the task executor
     * @return the step
     */
    public Step multiThreadStep(ImportStreetReader reader, ImportStreetWriter writer,
            StepBuilderFactory stepBuilderFactory, StreetImportListener streetImportListener,
            @Autowired StreetSkipPolicy streetSkipPolicy, Resource resource, TaskExecutor taskExecutor

    ) {

        return stepBuilderFactory.get("chunkStep").<Street, Street>chunk(10).reader(reader.reader(resource))
                .writer(writer).faultTolerant().skipPolicy(streetSkipPolicy).listener(streetImportListener)
                .taskExecutor(taskExecutor).build();
    }
}
