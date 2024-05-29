package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.model.entity.Street;

import com.viewnext.springbatchf.step.chunk.StreetItemProcessor;
import com.viewnext.springbatchf.step.chunk.StreetItemReader;
import com.viewnext.springbatchf.step.chunk.StreetItemWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;

/**
 * The type Street chunk step.
 */
public class StreetChunkStep {

    /**
     * Chunk step step.
     *
     * @param customerItemReader
     *         the customer item reader
     * @param processor
     *         the processor
     * @param writer
     *         the writer
     * @param stepBuilderFactory
     *         the step builder factory
     * @return the step
     */
    public Step chunkStep(
            FlatFileItemReader<Street> customerItemReader,
            StreetItemProcessor processor,
            StreetItemWriter writer,
            StepBuilderFactory stepBuilderFactory) {

        return stepBuilderFactory.get("customerChunkStep")
                //Assigns to this step a chunk of 10 entries
                .<Street, Street>chunk(10)
                //Assigns the reader for the entries
                .reader( customerItemReader)
                //Assigns the processor for the entries the reader passed.
                .processor(processor)
                //Finally, assigns the writer for the already processed entries.
                .writer(writer).build();
    }
}
