package com.viewnext.batch01.config.step;

import com.viewnext.batch01.model.Tramo;
import com.viewnext.batch01.step.chunk.TramoDistrictFilterer;
import com.viewnext.batch01.step.listener.TramoReadErrorLogger;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import static com.viewnext.batch01.util.InjectablePlaceholders.STRING_PLACEHOLDER;

@Configuration
public class StepConfig {

    @Value("${batch01.tramo-input-file}")
    private String tramoInputFile;

    private final StepBuilderFactory steps;

    public StepConfig(StepBuilderFactory steps) {
        this.steps = steps;
    }

    @Bean
    public TaskExecutor stepAsyncTaskExecutor() {
        return new SimpleAsyncTaskExecutor("batch01-steps");
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
    public TramoReadErrorLogger tramoReadLogger() {
        return new TramoReadErrorLogger();
    }

    @Bean
    @StepScope
    public TramoDistrictFilterer tramoFilterer
            (@Value("#{jobParameters['batch01.district_filter.district']}") String districtName) {
        return new TramoDistrictFilterer(districtName);
    }

    @Bean
    public Step districtFilterStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                   MongoItemWriter<Tramo> tramoWriter) {
        return steps.get("district_filter-step01-district_filter")
                .repository(jobRepository)
                .<Tramo, Tramo>chunk(16)
                .reader(tramoCsvReader())
                .listener(tramoReadLogger())
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .processor(tramoFilterer(STRING_PLACEHOLDER))
                .writer(tramoWriter)
                .transactionManager(transactionManager)
                .build();
    }

}
