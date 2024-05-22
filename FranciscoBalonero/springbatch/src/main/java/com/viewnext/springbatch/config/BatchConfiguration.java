package com.viewnext.springbatch.config;

import com.viewnext.springbatch.job.JobListener;
import com.viewnext.springbatch.model.Calle;
import com.viewnext.springbatch.step.CalleItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
public class BatchConfiguration {
    @Value("${file_input}")
    private String fileInput;

    @Bean
    public FlatFileItemReader<Calle> calleReader() {

        return new FlatFileItemReaderBuilder<Calle>().name("CalleItemReader").resource(new ClassPathResource(fileInput))
                .linesToSkip(1).delimited()
                .names("codigoCalle", "tipoVia", "nombreCalle", "primerNumTramo", "ultimoNumTramo", "barrio",
                        "codDistrito", "nomDistrito").fieldSetMapper(new BeanWrapperFieldSetMapper<Calle>() {{
                    setTargetType(Calle.class);
                }}).build();
    }

    @Bean
    public CalleItemProcessor processor() {
        return new CalleItemProcessor();
    }

    @Bean
    public JpaItemWriter<Calle> writer(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Calle> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public Job importCalle(JobRepository jobRepository, JobListener listener, Step step1) {
        return new JobBuilder("importUserJob").repository(jobRepository).incrementer(new RunIdIncrementer())
                .listener(listener).flow(step1).end().build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            JpaItemWriter<Calle> writer) {
        return new StepBuilder("step1").repository(jobRepository).<Calle, Calle>chunk(10).reader(calleReader())
                .processor(processor()).writer(writer).transactionManager(transactionManager).build();
    }

}