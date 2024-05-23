package com.viewnext.springbatchf.config;

import com.viewnext.springbatchf.job.ExportJob;
import com.viewnext.springbatchf.job.ImportJob;
import com.viewnext.springbatchf.job.MultithreadImportJob;
import com.viewnext.springbatchf.job.listeners.JobListener;
import com.viewnext.springbatchf.job.listeners.JobSkipListener;
import com.viewnext.springbatchf.model.Calle;
import com.viewnext.springbatchf.step.processors.DistritoProcessor1;
import com.viewnext.springbatchf.step.processors.DistritoProcessor2;
import com.viewnext.springbatchf.step.processors.MongoProcessor;
import com.viewnext.springbatchf.step.readers.CalleReader;
import com.viewnext.springbatchf.step.readers.MongoReader;
import com.viewnext.springbatchf.step.steps.ImportStep;
import com.viewnext.springbatchf.step.steps.MultithreadImportStep;
import com.viewnext.springbatchf.step.steps.StepExport;
import com.viewnext.springbatchf.step.writers.CsvWriter;
import com.viewnext.springbatchf.step.writers.MongoWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Configuration class for Spring Batch application. This class is responsible for configuring the Spring Batch
 * application, including the creation of jobs, steps, and readers/writers.
 *
 * @author Manuel Mateos de Torres
 */
@EnableAutoConfiguration
@EnableBatchProcessing
@Configuration
@RequiredArgsConstructor
public class ApplicationAutoConfig {

    /**
     * Input file path
     */
    @Value("${file.input}")
    private String fileInput;

    /**
     * MongoDB url
     */
    @Value("${mongodb.url}")
    private String url;

    /**
     * MongoDB Collection name
     */
    @Value("${collection.distrito.especifico}")
    private String collectionDistritoEspecifico;

    /**
     * MongoDB Collection name
     */
    @Value("${collection.otros.distritos}")
    private String collectionOtrosDistritos;

    @Value("${collection.multithread}")
    private String collectionMultithread;

    /**
     * CSV file path
     */
    @Value("${csv.distrito.especifico}")
    private String csvDistritoEspecifico;

    /**
     * CSV file path
     */
    @Value("${csv.otros.distritos}")
    private String csvOtrosDistritos;

    /**
     * MongoTemplate instance
     */
    private final MongoTemplate mongoTemplate;

    /**
     * JobRepository instance
     */
    private final JobRepository jobRepository;

    /**
     * PlatformTransactionManager instance
     */
    private final PlatformTransactionManager transactionManager;

    /**
     * PlatformTransactionManager instance
     */

    /**
     * Creates a MongoDataSource instance
     *
     * @return MongoDataSource instance
     */
    @Bean
    public MongoDataSource mongoDataSource() {
        return new MongoDataSource(url);
    }

    /**
     * Creates a FlatFileItemReader instance for reading input file
     *
     * @return FlatFileItemReader instance
     */
    @Bean
    public FlatFileItemReader<Calle> reader() {
        return new CalleReader().reader(fileInput);
    }

    /**
     * Creates a MongoItemReader instance for reading specific district data from MongoDB
     *
     * @return MongoItemReader instance
     */
    @Bean
    public MongoItemReader distritoEspecificoMongoReader() {
        return new MongoReader(mongoTemplate).createMongoItemReader(collectionDistritoEspecifico);
    }

    /**
     * Creates a MongoItemReader instance for reading the rest districts data from MongoDB
     *
     * @return MongoItemReader instance
     */
    @Bean
    public MongoItemReader otrosDistritosMongoReader() {
        return new MongoReader(mongoTemplate).createMongoItemReader(collectionOtrosDistritos);
    }

    /**
     * Creates a DistritoProcessor1 instance
     *
     * @return DistritoProcessor1 instance
     */
    @Bean
    public DistritoProcessor1 distritoProcessor1() {
        return new DistritoProcessor1();
    }

    /**
     * Creates a DistritoProcessor2 instance
     *
     * @return DistritoProcessor2 instance
     */
    @Bean
    public DistritoProcessor2 distritoProcessor2() {
        return new DistritoProcessor2();
    }

    /**
     * Creates a MongoProcessor instance
     *
     * @return MongoProcessor instance
     */
    @Bean
    public MongoProcessor mongoProcessor() {
        return new MongoProcessor();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("spring_batch");
    }

    /**
     * Creates a JobSkipListener instance
     *
     * @return JobSkipListener instance
     */
    @Bean
    JobSkipListener jobSkipListener() {
        return new JobSkipListener();
    }

    /**
     * Creates a JobListener instance
     *
     * @return JobListener instance
     */
    @Bean
    JobListener jobListener() {
        return new JobListener(mongoTemplate, distritoProcessor1().getDistritoCounts());
    }

    /**
     * Creates a MongoWriter instance To write data from objects with a specific district to MongoDB Collection
     *
     * @return MongoWriter instance
     */
    @Bean
    public MongoWriter mongoWriterDistritoEspecifico() {
        return new MongoWriter(mongoTemplate, collectionDistritoEspecifico);
    }

    /**
     * Creates a MongoWriter instance To write data of the rest objects to MongoDB Collection
     *
     * @return MongoWriter instance
     */
    @Bean
    public MongoWriter mongoWriterOtrosDistritos() {
        return new MongoWriter(mongoTemplate, collectionOtrosDistritos);
    }

    @Bean
    public MongoWriter mongoWriterMultithread() {
        return new MongoWriter(mongoTemplate, collectionMultithread);
    }

    /**
     * Creates a FlatFileItemWriter instance for distritoEspcifico Collection data to CSV file
     *
     * @return FlatFileItemWriter instance
     */
    @Bean
    public FlatFileItemWriter distritoEspecificoCsvWriter() {
        return new CsvWriter().csvWriter(csvDistritoEspecifico);
    }

    /**
     * Creates a FlatFileItemWriter instance for otrosDistritos Collection data to CSV file
     *
     * @return FlatFileItemWriter instance
     */
    @Bean
    public FlatFileItemWriter otrosDistritosCsvWriter() {
        return new CsvWriter().csvWriter(csvOtrosDistritos);
    }

    /**
     * Creates an ImportStep instance for import data from CSV file to MongoDB
     *
     * @return ExportStep instance
     */
    @Bean
    public Step importStep1() {
        return new ImportStep().importStep(jobRepository, transactionManager, reader(), distritoProcessor1(),
                mongoWriterDistritoEspecifico(), jobSkipListener());
    }

    /**
     * Creates an ImportStep instance for import data from CSV file to MongoDB
     *
     * @return ExportStep instance
     */
    @Bean
    public Step importStep2() {
        return new ImportStep().importStep(jobRepository, transactionManager, reader(), distritoProcessor2(),
                mongoWriterOtrosDistritos(), jobSkipListener());
    }

    @Bean
    public Step importStepMultithread() {
        return new MultithreadImportStep().importStep(jobRepository, transactionManager, reader(),
                mongoWriterMultithread(), jobSkipListener(), taskExecutor());
    }

    /**
     * Creates an ExportStep instance for exporting data from MongoDB to CSV file
     *
     * @return ExportStep instance
     */
    @Bean
    public Step exportStep1() {
        return new StepExport().exportStep(jobRepository, transactionManager, distritoEspecificoMongoReader(),
                mongoProcessor(), distritoEspecificoCsvWriter());
    }

    /**
     * Creates an ExportStep instance for exporting data from MongoDB to CSV file
     *
     * @return ExportStep instance
     */
    @Bean
    public Step exportStep2() {
        return new StepExport().exportStep(jobRepository, transactionManager, otrosDistritosMongoReader(),
                mongoProcessor(), otrosDistritosCsvWriter());
    }

    /**
     * Creates an ImportJob instance for importing data from input file to MongoDB
     *
     * @return ImportJob instance
     */
    @Bean
    public Job importJob() {
        return new ImportJob().importJob(jobRepository, jobListener(), importStep1(), importStep2());
    }

    /**
     * Creates an ExportJob instance for exporting data from MongoDB to CSV file
     *
     * @return ExportJob instance
     */
    @Bean
    public Job exportJob() {
        return new ExportJob().exportJob(jobRepository, exportStep1(), exportStep2());
    }

    @Bean
    public Job multithreadImportJob() {
        return new MultithreadImportJob().importJob(jobRepository, jobListener(), importStepMultithread());
    }
}