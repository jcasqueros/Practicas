package com.viewnext.springbatchf.config;

import com.viewnext.springbatchf.job.ImportJob;
import com.viewnext.springbatchf.job.JobListener;
import com.viewnext.springbatchf.job.MongoDataSource;
import com.viewnext.springbatchf.model.Calle;
import com.viewnext.springbatchf.step.CalleReader;
import com.viewnext.springbatchf.step.DistritoProcessor;
import com.viewnext.springbatchf.step.DistritoWriter;
import com.viewnext.springbatchf.step.Step1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@EnableAutoConfiguration
@Configuration
public class ApplicationAutoConfig {

    @Value("${file.input}")
    private String fileInput;

    @Value("${distrito}")
    String distrito;

    @Bean
    public MongoDataSource mongoDataSource() {
        return new MongoDataSource();
    }

    @Bean
    public ItemProcessor<Calle, Calle> distritoProcessor(@Value("${distrito}") String distrito) {
        DistritoProcessor processor = new DistritoProcessor();
        processor.setDistrito(distrito);
        return processor;
    }

    @Bean
    public ImportJob importJobBean() {
        return new ImportJob();
    }

    @Bean
    public Job importJob(ImportJob importJob, JobRepository jobRepository, JobListener listener, Step step1) {
        return importJob.importJob(jobRepository, listener, step1);
    }

    @Bean
    public FlatFileItemReader reader() {
        return new CalleReader().reader(fileInput);
    }

    @Bean
    public Step1 step1Bean() {
        return new Step1();
    }

    @Bean
    public Step step1(Step1 step1, JobRepository jobRepository, PlatformTransactionManager transactionManager,
            @Qualifier("reader") ItemReader<Calle> reader,
            @Qualifier("distritoProcessor") ItemProcessor<Calle, Calle> processor,
            @Qualifier("distritoWriter") ItemWriter<Calle> writer) {
        return step1.step1(jobRepository, transactionManager, reader, processor, writer);
    }

    @Bean
    public DistritoWriter distritoWriter(MongoTemplate mongoTemplate) {
        return new DistritoWriter(mongoTemplate);
    }
}