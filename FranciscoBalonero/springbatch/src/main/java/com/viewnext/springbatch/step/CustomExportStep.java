package com.viewnext.springbatch.step;

import com.viewnext.springbatch.model.Calle;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * This class is responsible for creating a custom step for exporting {@link Calle} data.
 *
 * <p>It uses Spring Batch's {@link StepBuilder} to create a step that reads from a data source, processes the data
 * using an item processor, and writes to a flat file.</p>
 *
 * @author Francisco Balonero Olivera
 */
public class CustomExportStep {

    /**
     * Creates a custom step for exporting {@link Calle} data.
     *
     * <p>The step is configured to read from a data source, process the data using the provided item processor, and
     * write to a flat file. It also uses a fault-tolerant transaction manager to handle errors.</p>
     *
     * @param jobRepository
     *         the job repository to use
     * @param transactionManager
     *         the transaction manager to use
     * @param reader
     *         the item reader to use
     * @param processor
     *         the item processor to use
     * @param writer
     *         the flat file item writer to use
     * @return the custom step
     */
    public Step customStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            ItemReader<Calle> reader, ItemProcessor<Calle, Calle> processor, FlatFileItemWriter<Calle> writer) {
        return new StepBuilder("step1").repository(jobRepository).<Calle, Calle>chunk(10).reader(reader)
                .processor(processor).writer(writer).faultTolerant().transactionManager(transactionManager).build();
    }
}
