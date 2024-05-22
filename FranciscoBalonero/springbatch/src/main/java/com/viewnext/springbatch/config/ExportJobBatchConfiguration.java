package com.viewnext.springbatch.config;

import com.viewnext.springbatch.job.ExportCalleJob;
import com.viewnext.springbatch.job.ExportJobListener;
import com.viewnext.springbatch.model.Calle;
import com.viewnext.springbatch.step.CustomExportStep;
import com.viewnext.springbatch.step.processor.CalleExportProcessor;
import com.viewnext.springbatch.step.reader.CalleMongoItemReader;
import com.viewnext.springbatch.step.writer.CalleCsvItemWriter;
import com.viewnext.springbatch.values.Values;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@AllArgsConstructor
public class ExportJobBatchConfiguration {

    private final Values values;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    private final MongoTemplate mongoTemplate;

    @Bean
    public ExportJobListener exportJobListener() {
        return new ExportJobListener();
    }

    @Bean
    @StepScope
    public MongoItemReader<Calle> mongoDistritoItemReader() {
        return new CalleMongoItemReader().mongoItemReader(values.getDistrito(), mongoTemplate);
    }

    @Bean
    @StepScope
    public MongoItemReader<Calle> mongoOthersItemReader() {
        return new CalleMongoItemReader().mongoItemReader("others", mongoTemplate);
    }

    @Bean
    public CalleExportProcessor calleExportProcessor() {
        return new CalleExportProcessor();
    }

    @Bean
    public FlatFileItemWriter<Calle> distritoCsvWriter() {
        return new CalleCsvItemWriter().csvItemWriter(values.getDistrito());
    }

    @Bean
    public FlatFileItemWriter<Calle> othersCsvWriter() {
        return new CalleCsvItemWriter().csvItemWriter(values.getOthersFileOutput());
    }

    @Bean
    public Step exportStep1() {
        return new CustomExportStep().customStep(jobRepository, transactionManager, mongoDistritoItemReader(),
                calleExportProcessor(), distritoCsvWriter());
    }

    @Bean
    public Step exportStep2() {
        return new CustomExportStep().customStep(jobRepository, transactionManager, mongoOthersItemReader(),
                calleExportProcessor(), othersCsvWriter());
    }

    @Bean
    public Job exportCalle() {
        return new ExportCalleJob().exportCalle(jobRepository, exportJobListener(), exportStep1(), exportStep2());
    }

}
