package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.model.entity.Street;

import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetProcessor;
import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetReader;
import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetWriter;
import com.viewnext.springbatchf.step.listener.StreetImportListener;
import com.viewnext.springbatchf.step.skippolicy.StreetSkipPolicy;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

/**
 * The type Street chunk step.
 */
public class ImportStep {



    /**
     * Import step.
     *
     * @param reader
     *         the reader
     * @param processor
     *         the processor
     * @param writer
     *         the writer
     * @param stepBuilderFactory
     *         the step builder factory
     * @param streetImportListener
     *         the street import listener
     * @param streetSkipPolicy
     *         the street skip policy
     * @return the step
     */
    public Step importStep(ImportStreetReader reader, ImportStreetProcessor processor, ImportStreetWriter writer,
            StepBuilderFactory stepBuilderFactory, StreetImportListener streetImportListener,
            @Autowired StreetSkipPolicy streetSkipPolicy,
            Resource resource

    ) {

        return stepBuilderFactory.get("chunkStep").<Street, Street>chunk(10)
                .reader(reader.reader(resource)).processor(processor).writer(writer).faultTolerant()
                .skipPolicy(streetSkipPolicy).listener(streetImportListener).build();
    }
}
