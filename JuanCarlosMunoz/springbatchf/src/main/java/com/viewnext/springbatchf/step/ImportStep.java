package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.model.entity.Street;

import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetProcessor;
import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetWriter;
import com.viewnext.springbatchf.step.listener.StreetImportListener;
import com.viewnext.springbatchf.step.skippolicy.StreetSkipPolicy;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The type Street chunk step.
 */
public class ImportStep {




    public Step chunkStep(
            FlatFileItemReader<Street> customerItemReader,
            ImportStreetProcessor processor,
            ImportStreetWriter writer,
            StepBuilderFactory stepBuilderFactory,
            StreetImportListener streetImportListener,
            @Autowired StreetSkipPolicy streetSkipPolicy

            ) {

        return stepBuilderFactory.get("chunkStep")
                .<Street, Street>chunk(10)
                .reader( customerItemReader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipPolicy(streetSkipPolicy)
                .listener(streetImportListener)
                .build();
    }
}
