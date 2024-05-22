package com.viewnext.batch01.config;

import com.viewnext.batch01.config.mongodb.MongoDbConfig;
import com.viewnext.batch01.job.listener.DistrictFilterJobListener;
import com.viewnext.batch01.model.Tramo;
import com.viewnext.batch01.model.history.DistrictFilterHistoryEntry;
import com.viewnext.batch01.step.chunk.TramoDistrictFilterer;
import com.viewnext.batch01.step.listener.TramoReadLogger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.data.MongoItemWriter;
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

/**
 * Global configuration for our Spring Batch application.
 *
 * @author Antonio Gil
 */
@Configuration
@Import({MongoDbConfig.class})
@EnableAutoConfiguration
@EnableBatchProcessing
public class SimpleBatchAppConfig {

    private static final String INJECTABLE_PLACEHOLDER_STRING = null;
    private static final MongoItemWriter<DistrictFilterHistoryEntry> INJECTABLE_PLACEHOLDER_HISTORY_WRITER = null;

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
    @JobScope
    public DistrictFilterJobListener filterJobListener(MongoItemWriter<DistrictFilterHistoryEntry> writer,
                                                       @Value("#{jobParameters['batch01.district']}") String district) {
        return new DistrictFilterJobListener(writer, district);
    }

    @Bean
    public Step districtFilterStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                   MongoItemWriter<Tramo> tramoWriter) {
        return new StepBuilder("district_filter-step01-district_filter")
                .repository(jobRepository)
                .<Tramo, Tramo>chunk(16)
                .reader(tramoCsvReader())
                .listener(tramoReadLogger())
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .processor(tramoFilterer(INJECTABLE_PLACEHOLDER_STRING))
                .writer(tramoWriter)
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Job districtFilterJob(JobRepository repository, PlatformTransactionManager transactionManager,
                             MongoItemWriter<Tramo> writer) {
        return new JobBuilder("district_filter")
                .repository(repository)
                .flow(districtFilterStep(repository, transactionManager, writer))
                .end()
                .listener(filterJobListener(INJECTABLE_PLACEHOLDER_HISTORY_WRITER, INJECTABLE_PLACEHOLDER_STRING))
                .build();
    }

}
