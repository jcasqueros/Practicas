package com.viewnext.springbatch.step;

import com.viewnext.springbatch.job.JobSkipListener;
import com.viewnext.springbatch.model.Calle;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * This class is responsible for creating a custom step for importing {@link Calle} data.
 *
 * <p>It uses Spring Batch's {@link StepBuilder} to create a step that reads from a flat file, processes the data using
 * an item processor, and writes to a MongoDB collection.</p>
 *
 * @author Francisco Balonero Olivera
 */
public class CustomImportStep {

    /**
     * Creates a custom step for importing {@link Calle} data.
     *
     * <p>The step is configured to read from a flat file, process the data using the provided item processor, and
     * write
     * to a MongoDB collection. It also uses a job skip listener to handle skips.</p>
     *
     * @param jobRepository
     *         the job repository to use
     * @param transactionManager
     *         the transaction manager to use
     * @param reader
     *         the flat file item reader to use
     * @param processor
     *         the item processor to use
     * @param writer
     *         the MongoDB item writer to use
     * @param skipListener
     *         the job skip listener to use
     * @return the custom step
     */
    public Step customStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            FlatFileItemReader<Calle> reader, ItemProcessor<Calle, Calle> processor, MongoItemWriter<Calle> writer,
            JobSkipListener skipListener) {
        return new StepBuilder("step1").repository(jobRepository).<Calle, Calle>chunk(10).reader(reader)
                .processor(processor).writer(writer).faultTolerant().listener(skipListener)
                .skipPolicy(new AlwaysSkipItemSkipPolicy()).transactionManager(transactionManager).build();
    }
}
