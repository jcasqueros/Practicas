package com.viewnext.springbatchf.config;

import com.viewnext.springbatchf.model.entity.Street;
import com.viewnext.springbatchf.step.chunk.StreetItemProcessor;
import com.viewnext.springbatchf.step.chunk.StreetItemReader;
import com.viewnext.springbatchf.step.chunk.StreetItemWriter;
import com.viewnext.springbatchf.step.listener.StreetStepExecutionListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;

import com.viewnext.springbatchf.job.StreetJob;
import com.viewnext.springbatchf.step.StreetChunkStep;
import org.springframework.core.io.ResourceLoader;

@Configuration
@EnableAutoConfiguration
public class SpringBatchConfig {




    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, Step streetChunkStep) {
        var customerJob = new StreetJob();
        return customerJob.job(jobBuilderFactory, streetChunkStep);
    }

    @Bean
    public Step streetChunkStep(
            FlatFileItemReader<Street> customerItemReader,
            StreetItemProcessor processor,
            StreetItemWriter writer,
            StepBuilderFactory stepBuilderFactory,
            StreetStepExecutionListener streetStepExecutionListener
            ) {
        var step = new StreetChunkStep();
        return step.chunkStep(customerItemReader, processor, writer, stepBuilderFactory,streetStepExecutionListener);
    }

    @Bean
    public StreetItemWriter writer()  {
        return new StreetItemWriter();
    }


    @Bean
    public FlatFileItemReader<Street> itemReaderCsv (ResourceLoader resourceLoader) {
        var reader = new StreetItemReader();
        return reader.reader(resourceLoader);
    }
    @Bean
    public StreetItemProcessor itemProcessor () {
        return new StreetItemProcessor();
    }




}
