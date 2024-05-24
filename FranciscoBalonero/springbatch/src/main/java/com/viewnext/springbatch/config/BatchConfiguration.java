package com.viewnext.springbatch.config;

import com.viewnext.springbatch.job.*;
import com.viewnext.springbatch.model.Calle;
import com.viewnext.springbatch.step.CustomExportStep;
import com.viewnext.springbatch.step.CustomImportStep;
import com.viewnext.springbatch.step.MultiThreadStep;
import com.viewnext.springbatch.step.processor.CalleExportProcessor;
import com.viewnext.springbatch.step.processor.CalleImportStep1ItemProcessor;
import com.viewnext.springbatch.step.processor.CalleImportStep2ItemProcessor;
import com.viewnext.springbatch.step.reader.CalleCsvItemReader;
import com.viewnext.springbatch.step.reader.CalleMongoItemReader;
import com.viewnext.springbatch.step.writer.CalleCsvItemWriter;
import com.viewnext.springbatch.step.writer.CalleMongoItemWriter;
import com.viewnext.springbatch.values.Values;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * This class is responsible for configuring the Spring Batch application.
 *
 * <p>It uses Spring Boot's {@link Configuration} annotation to enable batch processing and auto-configuration.</p>
 *
 * @author Francisco Balonero Olivera
 */
@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfiguration {

    /**
     * The values object that holds the application's configuration values.
     */
    private final Values values;

    /**
     * The job repository to use for storing and retrieving job instances.
     */
    private final JobRepository jobRepository;

    /**
     * The platform transaction manager to use for managing transactions.
     */
    private final PlatformTransactionManager transactionManager;

    /**
     * The MongoTemplate instance to use for interacting with the MongoDB database.
     */
    private final MongoTemplate mongoTemplate;

    //-----------------------JOB PARA IMPORTAR LAS CALLES DEL CSV A LA BASE DE DATOS-----------------------
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    //VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV

    /**
     * Creates a job skip listener bean.
     *
     * <p>This listener is responsible for handling skip events during job execution.</p>
     *
     * @return the job skip listener bean
     */
    @Bean
    public JobSkipListener skipListener() {
        return new JobSkipListener();
    }

    /**
     * Creates an import job listener bean.
     *
     * <p>This listener is responsible for handling events during the import job execution.</p>
     *
     * @return the import job listener bean
     */
    @Bean
    public ImportJobListener importJobListener() {
        return new ImportJobListener(values.getDistritoEspecifico(), mongoTemplate,
                processorStep1().getDistritoCounts(), values.getDistritosRestantes());
    }

    /**
     * Creates a flat file item reader bean for reading Calle data from a CSV file.
     *
     * <p>This reader is used in the import step to read Calle data from a CSV file.</p>
     *
     * @return the flat file item reader bean
     */
    @Bean
    public FlatFileItemReader<Calle> csvReader() {
        return new CalleCsvItemReader().calleReader(values.getFileInput());
    }

    /**
     * Creates a Calle import step 1 item processor bean.
     *
     * <p>This processor is used in the import step to process Calle data.</p>
     *
     * @return the Calle import step 1 item processor bean
     */
    @Bean
    public CalleImportStep1ItemProcessor processorStep1() {
        return new CalleImportStep1ItemProcessor(values.getDistritoEspecifico());
    }

    /**
     * Creates a Calle import step 2 item processor bean.
     *
     * <p>This processor is used in the import step to process Calle data.</p>
     *
     * @return the Calle import step 2 item processor bean
     */
    @Bean
    public CalleImportStep2ItemProcessor processorStep2() {
        return new CalleImportStep2ItemProcessor(values.getDistritoEspecifico());
    }

    /**
     * Creates a Mongo item writer bean for writing Calle data to a MongoDB collection.
     *
     * <p>This writer is used in the import step to write Calle data to a MongoDB collection.</p>
     *
     * @return the Mongo item writer bean
     */
    @Bean
    public MongoItemWriter<Calle> distritoMongoWriter() {
        return new CalleMongoItemWriter(values.getDistritoEspecifico(), mongoTemplate);
    }

    /**
     * Creates a Mongo item writer bean for writing Calle data to a MongoDB collection.
     *
     * <p>This writer is used in the import step to write Calle data to a MongoDB collection.</p>
     *
     * @return the Mongo item writer bean
     */
    @Bean
    public MongoItemWriter<Calle> othersDistritoMongoWriter() {
        return new CalleMongoItemWriter(values.getDistritosRestantes(), mongoTemplate);
    }

    /**
     * Creates an import step bean.
     *
     * <p>This step is used in the import job to read Calle data from a CSV file, process it, and write it to a MongoDB
     * collection.</p>
     *
     * @return the import step bean
     */
    @Bean
    public Step importStep1() {
        return new CustomImportStep().customStep(jobRepository, transactionManager, csvReader(), processorStep1(),
                distritoMongoWriter(), skipListener());
    }

    /**
     * Creates an import step bean.
     *
     * <p>This step is used in the import job to read Calle data from a CSV file, process it, and write it to a MongoDB
     * collection.</p>
     *
     * @return the import step bean
     */
    @Bean
    public Step importStep2() {
        return new CustomImportStep().customStep(jobRepository, transactionManager, csvReader(), processorStep2(),
                othersDistritoMongoWriter(), skipListener());
    }

    /**
     * Creates an import job bean.
     *
     * <p>This job is used to import Calle data from a CSV file to a MongoDB collection.</p>
     *
     * @return the import job bean
     */
    @Bean
    public Job importCalle() {
        return new ImportCalleJob().importCalle(jobRepository, importJobListener(), importStep1(), importStep2());
    }

    //-----------------------JOB PARA EXPORTAR LAS CALLES DE LA BASE DE DATOS AL CSV-----------------------
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    //VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV

    /**
     * Creates Default listener to jobs.
     *
     * <p>Default listener.</p>
     *
     * @return the default job listener bean
     */
    @Bean
    public DefaultJobListener defaultJobListener() {
        return new DefaultJobListener();
    }

    /**
     * Creates a Mongo item reader bean for reading Calle data from a MongoDB collection.
     *
     * <p>This reader is used in the export step to read Calle data from a MongoDB collection.</p>
     *
     * @return the Mongo item reader bean
     */
    @Bean
    @StepScope
    public MongoItemReader<Calle> mongoDistritoItemReader() {
        return new CalleMongoItemReader().mongoItemReader(values.getDistritoEspecifico(), mongoTemplate);
    }

    /**
     * Creates a Mongo item reader bean for reading Calle data from a MongoDB collection.
     *
     * <p>This reader is used in the export step to read Calle data from a MongoDB collection.</p>
     *
     * @return the Mongo item reader bean
     */
    @Bean
    @StepScope
    public MongoItemReader<Calle> mongoOthersItemReader() {
        return new CalleMongoItemReader().mongoItemReader(values.getDistritosRestantes(), mongoTemplate);
    }

    /**
     * Creates a Calle export processor bean.
     *
     * <p>This processor is used in the export step to process Calle data.</p>
     *
     * @return the Calle export processor bean
     */
    @Bean
    public CalleExportProcessor calleExportProcessor() {
        return new CalleExportProcessor();
    }

    /**
     * Creates a flat file item writer bean for writing Calle data to a CSV file.
     *
     * <p>This writer is used in the export step to write Calle data to a CSV file.</p>
     *
     * @return the flat file item writer bean
     */
    @Bean
    public FlatFileItemWriter<Calle> distritoCsvWriter() {
        return new CalleCsvItemWriter().csvItemWriter(values.getDistritoEspecifico());
    }

    /**
     * Creates a flat file item writer bean for writing Calle data to a CSV file.
     *
     * <p>This writer is used in the export step to write Calle data to a CSV file.</p>
     *
     * @return the flat file item writer bean
     */
    @Bean
    public FlatFileItemWriter<Calle> othersCsvWriter() {
        return new CalleCsvItemWriter().csvItemWriter(values.getDistritosRestantes());
    }

    /**
     * Creates an export step bean.
     *
     * <p>This step is used in the export job to read Calle data from a MongoDB collection, process it, and write it to
     * a CSV file.</p>
     *
     * @return the export step bean
     */
    @Bean
    public Step exportStep1() {
        return new CustomExportStep().customStep(jobRepository, transactionManager, mongoDistritoItemReader(),
                calleExportProcessor(), distritoCsvWriter());
    }

    /**
     * Creates an export step bean.
     *
     * <p>This step is used in the export job to read Calle data from a MongoDB collection, process it, and write it to
     * a CSV file.</p>
     *
     * @return the export step bean
     */
    @Bean
    public Step exportStep2() {
        return new CustomExportStep().customStep(jobRepository, transactionManager, mongoOthersItemReader(),
                calleExportProcessor(), othersCsvWriter());
    }

    /**
     * Creates an export job bean.
     *
     * <p>This job is used to export Calle data from a MongoDB collection to a CSV file.</p>
     *
     * @return the export job bean
     */
    @Bean
    public Job exportCalle() {
        return new ExportCalleJob().exportCalle(jobRepository, defaultJobListener(), exportStep1(), exportStep2());

    }

    //-----------------------JOB MULTITHREAD IMPORTAR CSV A MONGO-----------------------
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    //VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV

    /**
     * Returns a task executor bean that allows for asynchronous task execution.
     *
     * @return a SimpleAsyncTaskExecutor instance
     */
    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    /**
     * Returns a MongoItemWriter bean that writes data to a MongoDB collection in a multithreaded manner.
     *
     * @return a CalleMongoItemWriter instance
     */
    @Bean
    public MongoItemWriter<Calle> multiThreadMongoWriter() {
        return new CalleMongoItemWriter("MULTITHREAD", mongoTemplate);
    }

    /**
     * Returns a Step bean that represents a multithreaded step in a batch job.
     *
     * @return a MultiThreadStep instance
     */
    @Bean
    public Step multiThreadStep() {
        return new MultiThreadStep().customStep(jobRepository, transactionManager, csvReader(),
                multiThreadMongoWriter(), skipListener(), taskExecutor());
    }

    /**
     * Returns a Job bean that represents a batch job that imports data from a CSV file to a MongoDB collection in a
     * multithreaded manner.
     *
     * @return a MultiThreadJob instance
     */
    @Bean
    public Job multiThreadJob() {
        return new MultiThreadJob().importCalleMultiThread(jobRepository, defaultJobListener(), multiThreadStep());
    }
}

