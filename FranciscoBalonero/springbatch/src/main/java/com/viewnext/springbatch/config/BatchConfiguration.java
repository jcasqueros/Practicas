package com.viewnext.springbatch.config;

import com.viewnext.springbatch.job.ImportCalleJob;
import com.viewnext.springbatch.job.JobListener;
import com.viewnext.springbatch.job.JobSkipListener;
import com.viewnext.springbatch.model.Calle;
import com.viewnext.springbatch.step.CalleItemProcessor;
import com.viewnext.springbatch.step.CalleItemReader;
import com.viewnext.springbatch.step.CalleItemWriter;
import com.viewnext.springbatch.step.Step1;
import com.viewnext.springbatch.values.Values;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Spring Batch configuration class.
 *
 * @author Francisco Balonero Olivera
 */
@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfiguration {

    /**
     * Values instance.
     */
    private Values values;

    /**
     * Job repository instance.
     */
    private JobRepository jobRepository;

    /**
     * Platform transaction manager instance.
     */
    private PlatformTransactionManager transactionManager;

    /**
     * Creates a new instance of JobSkipListener.
     *
     * @return a new instance of JobSkipListener
     */
    @Bean
    public JobSkipListener skipListener() {
        return new JobSkipListener();
    }

    /**
     * Creates a new instance of MongoTemplate.
     *
     * @return a new instance of MongoTemplate
     */
    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoConfig(values.getMongoUrl()).mongoTemplate();
    }

    /**
     * Creates a new instance of JobListener.
     *
     * @return a new instance of JobListener
     */
    @Bean
    public JobListener jobListener() {
        return new JobListener(mongoTemplate());
    }

    /**
     * Creates a new instance of FlatFileItemReader.
     *
     * @return a new instance of FlatFileItemReader
     */
    @Bean
    public FlatFileItemReader<Calle> reader() {
        return new CalleItemReader().calleReader(values.getFileInput());
    }

    /**
     * Creates a new instance of CalleItemProcessor.
     *
     * @return a new instance of CalleItemProcessor
     */
    @Bean
    public CalleItemProcessor processor() {
        return new CalleItemProcessor(values.getDistrito());
    }

    /**
     * Creates a new instance of MongoItemWriter.
     *
     * @return a new instance of MongoItemWriter
     */
    @Bean
    public MongoItemWriter<Calle> writer() {
        return new CalleItemWriter(mongoTemplate());
    }

    /**
     * Creates a new instance of Step.
     *
     * @return a new instance of Step
     */
    @Bean
    public Step step1() {
        return new Step1().step1(jobRepository, transactionManager, reader(), processor(), writer(), skipListener());
    }

    /**
     * Creates a new instance of Job.
     *
     * @return a new instance of Job
     */
    @Bean
    public Job importCalle() {
        return new ImportCalleJob().importCalle(jobRepository, jobListener(), step1());
    }
}
