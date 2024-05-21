package com.viewnext.batch01.config;

import com.viewnext.batch01.model.Tramo;
import com.viewnext.batch01.step.chunk.TramoDistrictFilterer;
import com.viewnext.batch01.step.chunk.TramoSetWriter;
import com.viewnext.batch01.step.listener.TramoReadLogger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Import({MongoDbConfig.class})
@EnableAutoConfiguration
@EnableBatchProcessing
public class SimpleBatchAppConfig {

    private static final String INJECTABLE_PLACEHOLDER = null;

    @Value("${batch01.tramo-input-file}")
    private String tramoInputFile;

    private final JobBuilderFactory jobs;

    private final StepBuilderFactory steps;

    public SimpleBatchAppConfig(JobBuilderFactory jobs, StepBuilderFactory steps) {
        this.jobs = jobs;
        this.steps = steps;
    }

    @Bean
    public FlatFileItemReader<Tramo> tramoCsvReader() {
        var fieldSetMapper = new BeanWrapperFieldSetMapper<Tramo>();
        fieldSetMapper.setTargetType(Tramo.class);

        return new FlatFileItemReaderBuilder<Tramo>()
                .name("tramoItemReader")
                .resource(new ClassPathResource(tramoInputFile))
                .linesToSkip(1)
                .delimited()
                .names("codigoCalle", "tipoVia", "nombreCalle", "primerNumeroTramo", "ultimoNumeroTramo", "barrio",
                        "codigoDistrito", "nombreDistrito")
                .fieldSetMapper(fieldSetMapper)
                .build();
    }

    @Bean
    public TramoReadLogger tramoReadLogger() {
        return new TramoReadLogger();
    }

    @Bean
    @StepScope
    public TramoDistrictFilterer tramoFilterer(@Value("#{jobParameters['batch01.district']}") String districtName) {
        return new TramoDistrictFilterer(districtName);
    }

    @Bean
    public TramoSetWriter tramoSetWriter() {
        return new TramoSetWriter();
    }

    @Bean
    public Step step01(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step01")
                .repository(jobRepository)
                .<Tramo, Tramo> chunk(16)
                .reader(tramoCsvReader())
                .listener(tramoReadLogger())
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .processor(tramoFilterer(INJECTABLE_PLACEHOLDER))
                .writer(tramoSetWriter())
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Job processCsvJob(JobRepository repository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("processCsvJob")
                .repository(repository)
                .flow(step01(repository, transactionManager))
                // TODO: Implement step02
                //.next(step02())
                .end()
                .build();
    }

}
