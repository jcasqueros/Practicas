package com.viewnext.springbatchf.step.steps;

import com.viewnext.springbatchf.job.listeners.JobSkipListener;
import com.viewnext.springbatchf.model.Calle;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * A class that builds a Step instance for importing Calle objects from a CSV file to a MongoDB collection.
 *
 * @author Manuel Mateos de Torres
 */
public class MultithreadImportStep {

    /**
     * Builds a Step instance for importing Calle objects from a CSV file to a MongoDB collection.
     *
     * @param jobRepository
     *         the JobRepository instance
     * @param transactionManager
     *         the PlatformTransactionManager instance
     * @param reader
     *         the ItemReader instance for reading Calle objects
     * @param writer
     *         the MongoItemWriter instance for writing Calle objects to a MongoDB collection
     * @param jobSkipListener
     *         the JobSkipListener instance for listening to skip events
     * @return a Step instance
     */
    public Step importStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            ItemReader<Calle> reader, MongoItemWriter<Calle> writer, JobSkipListener jobSkipListener,
            TaskExecutor taskExecutor) {
        return new StepBuilder("importStep").repository(jobRepository).<Calle, Calle>chunk(10).reader(reader)
                .writer(writer).faultTolerant().listener(jobSkipListener).skipPolicy(new AlwaysSkipItemSkipPolicy())
                .transactionManager(transactionManager).taskExecutor(taskExecutor).build();
    }
}