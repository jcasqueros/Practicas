package com.viewnext.batch01.config.step;

import com.viewnext.batch01.config.mongodb.MongoDbConfig;
import com.viewnext.batch01.job.history.DistrictFilterHistoryEntry;
import com.viewnext.batch01.model.Tramo;
import com.viewnext.batch01.step.chunk.TramoDistrictFilterer;
import com.viewnext.batch01.step.listener.TramoReadErrorLogger;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.PlatformTransactionManager;

import java.nio.file.Path;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.Map;

import static com.viewnext.batch01.util.InjectablePlaceholders.MONGO_TEMPLATE_PLACEHOLDER;
import static com.viewnext.batch01.util.InjectablePlaceholders.STRING_PLACEHOLDER;

/**
 * Configuration for batch job steps.
 *
 * @author Antonio Gil
 */
@Configuration
@Import({MongoDbConfig.class})
public class StepConfig {

    private static final int DEFAULT_CHUNK_SIZE = 256;
    private static final int DEFAULT_THROTTLE_LIMIT = 20;

    private final StepBuilderFactory steps;

    @Value("${batch01.tramo-input-file}")
    private String tramoInputFile;

    @Value("${batch01.output-folder-path}")
    private String outputFolderPath;

    public StepConfig(StepBuilderFactory steps) {
        this.steps = steps;
    }

    @Bean
    public TaskExecutor stepAsyncTaskExecutor() {
        return new SimpleAsyncTaskExecutor("batch01-steps");
    }

    @Bean
    public Step districtFilterStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return steps.get("district_filter-step01-district_filter")
                .repository(jobRepository)
                .<Tramo, Tramo>chunk(DEFAULT_CHUNK_SIZE)
                .reader(tramoCsvReader())
                .listener(tramoReadLogger())
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .processor(tramoFilterer(STRING_PLACEHOLDER))
                .writer(districtFilterWriter(MONGO_TEMPLATE_PLACEHOLDER, STRING_PLACEHOLDER))
                .transactionManager(transactionManager)
                .taskExecutor(stepAsyncTaskExecutor())
                .throttleLimit(DEFAULT_THROTTLE_LIMIT)
                .build();
    }

    @Bean
    public MongoItemWriter<DistrictFilterHistoryEntry> districtFilterHistoryWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<DistrictFilterHistoryEntry>().collection("_history-district_filter")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public FlatFileItemReader<Tramo> tramoCsvReader() {
        var fieldSetMapper = new BeanWrapperFieldSetMapper<Tramo>();
        fieldSetMapper.setTargetType(Tramo.class);

        return new FlatFileItemReaderBuilder<Tramo>().name("tramoCsvReader")
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
    public TramoDistrictFilterer tramoFilterer(@Value("#{jobParameters['batch01.district_filter.district']}")
                                               String districtName) {
        return new TramoDistrictFilterer(districtName);
    }

    @Bean
    @StepScope
    public MongoItemWriter<Tramo> districtFilterWriter(MongoTemplate mongoTemplate,
                                                       @Value("#{jobParameters['batch01.district_filter.district']}")
                                                       String districtName) {
        final long timestamp = Instant.now().toEpochMilli();
        final String collectionName = MessageFormat.format("district_filter-{0}-{1,number,#}", districtName,
                timestamp);

        return new MongoItemWriterBuilder<Tramo>()
                .template(mongoTemplate)
                .collection(collectionName)
                .build();
    }

    @Bean
    public Step dumpDistrictsToCsvStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return steps.get("db_dump-step01-dump_districts_to_csv")
                .repository(jobRepository)
                .<Tramo, Tramo>chunk(DEFAULT_CHUNK_SIZE)
                .reader(districtDumpDbReader(MONGO_TEMPLATE_PLACEHOLDER, STRING_PLACEHOLDER))
                .writer(tramoToCsvWriter(STRING_PLACEHOLDER))
                .transactionManager(transactionManager)
                .taskExecutor(stepAsyncTaskExecutor())
                .throttleLimit(DEFAULT_THROTTLE_LIMIT)
                .build();
    }

    @Bean
    public Step dumpFilterHistoryEntriesToCsvStep(JobRepository jobRepository,
                                                  PlatformTransactionManager transactionManager) {
        final long timestamp = Instant.now().toEpochMilli();

        return steps.get("db_dump-step02-dump_filter_entries_to_csv")
                .repository(jobRepository)
                .<DistrictFilterHistoryEntry, DistrictFilterHistoryEntry>chunk(DEFAULT_CHUNK_SIZE)
                .reader(districtFilterHistoryDbDumpReader(MONGO_TEMPLATE_PLACEHOLDER))
                .writer(filterHistoryEntriesToCsvWriter(
                        MessageFormat.format("_history-district_filter-{0,number,#}.csv", timestamp)))
                .transactionManager(transactionManager)
                .taskExecutor(stepAsyncTaskExecutor())
                .throttleLimit(DEFAULT_THROTTLE_LIMIT)
                .build();
    }

    @Bean
    @StepScope
    public MongoItemReader<Tramo> districtDumpDbReader(MongoTemplate mongoTemplate,
                                                       @Value("#{jobParameters['batch01.db_dump.source_collection']}")
                                                       String sourceCollectionName) {
        Query mongoQuery = new BasicQuery("{}");

        return new MongoItemReaderBuilder<Tramo>().name("districtDumpDbReader")
                .query(mongoQuery)
                .sorts(Map.of("ID_TRAMO", Sort.Direction.ASC))
                .targetType(Tramo.class)
                .template(mongoTemplate)
                .collection(sourceCollectionName)
                .build();
    }

    @Bean
    public MongoItemReader<DistrictFilterHistoryEntry> districtFilterHistoryDbDumpReader(MongoTemplate mongoTemplate) {
        Query mongoQuery = new BasicQuery("{}");

        return new MongoItemReaderBuilder<DistrictFilterHistoryEntry>().name("districtFilterHistoryDbDumpReader")
                .query(mongoQuery)
                .sorts(Map.of("TIMESTAMP", Sort.Direction.ASC))
                .targetType(DistrictFilterHistoryEntry.class)
                .template(mongoTemplate)
                .collection("_history-district_filter")
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<Tramo> tramoToCsvWriter(@Value("#{jobParameters['batch01.db_dump.source_collection']}")
                                                      String sourceName) {

        Path outputPath = Path.of(outputFolderPath, sourceName + ".csv");

        return new FlatFileItemWriterBuilder<Tramo>().name("tramoToCsvWriter")
                .resource(new FileSystemResource(outputPath))
                .delimited()
                .names("codigoCalle", "tipoVia", "nombreCalle", "primerNumeroTramo", "ultimoNumeroTramo", "barrio",
                        "codigoDistrito", "nombreDistrito")
                .build();
    }

    @Bean
    public FlatFileItemWriter<DistrictFilterHistoryEntry> filterHistoryEntriesToCsvWriter(String fileName) {
        Path outputPath = Path.of(outputFolderPath, fileName);

        return new FlatFileItemWriterBuilder<DistrictFilterHistoryEntry>().name("tramoToCsvWriter")
                .resource(new FileSystemResource(outputPath))
                .delimited()
                .names("timestamp", "districtName", "jobExitStatus", "persistedEntryCount")
                .build();
    }

}
