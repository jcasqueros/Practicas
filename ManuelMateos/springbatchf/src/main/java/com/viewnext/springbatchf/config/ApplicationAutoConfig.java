package com.viewnext.springbatchf.config;

import com.viewnext.springbatchf.job.ImportJob;
import com.viewnext.springbatchf.job.JobListener;
import com.viewnext.springbatchf.job.JobSkipListener;
import com.viewnext.springbatchf.model.Calle;
import com.viewnext.springbatchf.step.*;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@EnableAutoConfiguration
@EnableBatchProcessing
@Configuration
@RequiredArgsConstructor
public class ApplicationAutoConfig {

    @Value("${file.input}")
    private String fileInput;

    @Value("${mongodb.url}")
    private String url;

    private final MongoTemplate mongoTemplate;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    @Bean
    public MongoDataSource mongoDataSource() {
        return new MongoDataSource(url);
    }

    @Bean
    JobSkipListener jobSkipListener() {
        return new JobSkipListener();
    }

    @Bean
    public DistritoProcessor1 distritoProcessor1() {
        return new DistritoProcessor1();
    }

    @Bean
    public DistritoProcessor2 distritoProcessor2() {
        return new DistritoProcessor2();
    }

    @Bean
    JobListener jobListener() {
        return new JobListener(mongoTemplate, distritoProcessor1().getDistritoCounts());
    }

    @Bean
    public Job importJob() {
        return new ImportJob().importJob(jobRepository, jobListener(), step1(), step2());
    }

    @Bean
    public FlatFileItemReader<Calle> reader() {
        return new CalleReader().reader(fileInput);
    }

    @Bean
    public Step step1() {
        return new Step1().step1(jobRepository, transactionManager, reader(), distritoProcessor1(), distritoWriter1(),
                jobSkipListener());
    }

    @Bean
    public Step step2() {
        return new Step2().step2(jobRepository, transactionManager, reader(), distritoProcessor2(), distritoWriter2(),
                jobSkipListener());
    }

    @Bean
    public DistritoWriter1 distritoWriter1() {
        return new DistritoWriter1(mongoTemplate);
    }

    @Bean
    public DistritoWriter2 distritoWriter2() {
        return new DistritoWriter2(mongoTemplate);
    }
}