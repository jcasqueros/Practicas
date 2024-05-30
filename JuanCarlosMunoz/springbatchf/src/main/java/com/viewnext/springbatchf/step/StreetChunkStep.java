package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.model.entity.Street;

import com.viewnext.springbatchf.step.chunk.StreetItemProcessor;
import com.viewnext.springbatchf.step.chunk.StreetItemWriter;
import com.viewnext.springbatchf.step.listener.StreetStepExecutionListener;
import com.viewnext.springbatchf.step.skippolicy.StreetSkipPolicy;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;

/**
 * The type Street chunk step.
 */
public class StreetChunkStep {



    public Step chunkStep(
            FlatFileItemReader<Street> customerItemReader,
            StreetItemProcessor processor,
            StreetItemWriter writer,
            StepBuilderFactory stepBuilderFactory,
            StreetStepExecutionListener streetStepExecutionListener

            ) {

        return stepBuilderFactory.get("streetChunkStep")
                .<Street, Street>chunk(10)
                .reader( customerItemReader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipPolicy(new StreetSkipPolicy())
                .listener(streetStepExecutionListener)
                .build();
    }
}
