package com.viewnext.batch01.config;

import com.viewnext.batch01.step.listener.TramoReaderListener;
import com.viewnext.batch01.model.Tramo;
import com.viewnext.batch01.step.TramoItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
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
public class SimpleBatchAppConfiguration {

    private static final String INJECTABLE_PLACEHOLDER = null;

    @Value("${batch01.input-file}")
    private String inputFile;

    @Bean
    public FlatFileItemReader<Tramo> itemReader() {
        var fieldSetMapper = new BeanWrapperFieldSetMapper<Tramo>();
        fieldSetMapper.setTargetType(Tramo.class);

        return new FlatFileItemReaderBuilder<Tramo>()
                .name("tramoItemReader")
                .resource(new ClassPathResource(inputFile))
                .linesToSkip(1)
                .delimited()
                .names("codigoCalle", "tipoVia", "nombreCalle", "primerNumeroTramo", "ultimoNumeroTramo", "barrio",
                        "codigoDistrito", "nombreDistrito")
                .fieldSetMapper(fieldSetMapper)
                .build();
    }

    @Bean
    @StepScope
    public TramoItemProcessor itemProcessor(@Value("#{jobParameters['batch01.district']}") String districtName) {
        return new TramoItemProcessor(districtName);
    }

    @Bean
    public JpaItemWriter<Tramo> writer(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Tramo> writer = new JpaItemWriter<>();

        writer.setEntityManagerFactory(entityManagerFactory);

        return writer;
    }

    @Bean
    public Job importTramoJob(JobRepository repository, JobExecutionListener listener, Step step01) {
        return new JobBuilder("importTramoJob")
                .repository(repository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step01)
                .end()
                .build();
    }

    @Bean
    public Step step01(JobRepository repository, PlatformTransactionManager transactionManager,
                          JpaItemWriter<Tramo> writer) {
        return new StepBuilder("step01")
                .repository(repository)
                .<Tramo, Tramo> chunk(10)
                .reader(itemReader())
                .listener(new TramoReaderListener())
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skipLimit(Integer.MAX_VALUE)
                .processor(itemProcessor(INJECTABLE_PLACEHOLDER))
                .writer(writer)
                .transactionManager(transactionManager)
                .build();
    }

}
