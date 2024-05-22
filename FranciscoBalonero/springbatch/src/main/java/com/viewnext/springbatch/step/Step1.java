package com.viewnext.springbatch.step;

import com.viewnext.springbatch.job.JobSkipListener;
import com.viewnext.springbatch.model.Calle;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Step configuration for step 1 of the batch job.
 *
 * @author Francisco Balonero Olivera
 */
public class Step1 {

    /**
     * Creates a new step instance with the given configuration.
     *
     * @param jobRepository
     *         the job repository instance
     * @param transactionManager
     *         the transaction manager instance
     * @param reader
     *         the item reader instance
     * @param processor
     *         the item processor instance
     * @param writer
     *         the item writer instance
     * @param skipListener
     *         the skip listener instance
     * @return a new step instance
     */
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            FlatFileItemReader<Calle> reader, CalleItemProcessor processor, MongoItemWriter<Calle> writer,
            JobSkipListener skipListener) {
        return new StepBuilder("step1").repository(jobRepository).<Calle, Calle>chunk(10).reader(reader)
                .processor(processor).writer(writer).faultTolerant().listener(skipListener)
                .skipPolicy(new AlwaysSkipItemSkipPolicy()).transactionManager(transactionManager).build();
    }
}
