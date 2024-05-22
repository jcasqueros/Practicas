package com.viewnext.springbatch.config;

import com.viewnext.springbatch.job.ImportCalleJob;
import com.viewnext.springbatch.job.ImportJobListener;
import com.viewnext.springbatch.job.JobSkipListener;
import com.viewnext.springbatch.model.Calle;
import com.viewnext.springbatch.step.CustomImportStep;
import com.viewnext.springbatch.step.processor.CalleImportStep1ItemProcessor;
import com.viewnext.springbatch.step.processor.CalleImportStep2ItemProcessor;
import com.viewnext.springbatch.step.reader.CalleCsvItemReader;
import com.viewnext.springbatch.step.writer.CalleMongoItemWriter;
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

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@AllArgsConstructor
public class ImportJobBatchConfiguration {

    private final Values values;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    private final MongoTemplate mongoTemplate;

    @Bean
    public JobSkipListener skipListener() {
        return new JobSkipListener();
    }

    @Bean
    public ImportJobListener importJobListener() {
        return new ImportJobListener(values.getDistrito(), mongoTemplate, processorStep1().getDistritoCounts());
    }

    @Bean
    public FlatFileItemReader<Calle> csvReader() {
        return new CalleCsvItemReader().calleReader(values.getFileInput());
    }

    @Bean
    public CalleImportStep1ItemProcessor processorStep1() {
        return new CalleImportStep1ItemProcessor(values.getDistrito());
    }

    @Bean
    public CalleImportStep2ItemProcessor processorStep2() {
        return new CalleImportStep2ItemProcessor(values.getDistrito());
    }

    @Bean
    public MongoItemWriter<Calle> distritoMongoWriter() {
        return new CalleMongoItemWriter(values.getDistrito(), mongoTemplate);
    }

    @Bean
    public MongoItemWriter<Calle> othersDistritoMongoWriter() {
        return new CalleMongoItemWriter("others", mongoTemplate);
    }

    @Bean
    public Step importStep1() {
        return new CustomImportStep().customStep(jobRepository, transactionManager, csvReader(), processorStep1(),
                distritoMongoWriter(), skipListener());
    }

    @Bean
    public Step importStep2() {
        return new CustomImportStep().customStep(jobRepository, transactionManager, csvReader(), processorStep2(),
                othersDistritoMongoWriter(), skipListener());
    }

    @Bean
    public Job importCalle() {
        return new ImportCalleJob().importCalle(jobRepository, importJobListener(), importStep1(), importStep2());
    }

}
