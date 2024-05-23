package com.viewnext.springbatchf.step.steps;

import com.viewnext.springbatchf.model.Calle;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * A class that builds a Step instance for exporting Calle objects to a CSV file.
 *
 * @author Manuel Mateos de Torres
 */
public class StepExport {

    /**
     * Builds a Step instance for exporting Calle objects to a CSV file.
     *
     * @param jobRepository
     *         the JobRepository instance
     * @param transactionManager
     *         the PlatformTransactionManager instance
     * @param reader
     *         the ItemReader instance for reading Calle objects
     * @param processor
     *         the ItemProcessor instance for processing Calle objects
     * @param writer
     *         the FlatFileItemWriter instance for writing Calle objects to a flat file
     * @return a Step instance
     */
    public Step exportStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            ItemReader<Calle> reader, ItemProcessor<Calle, Calle> processor, FlatFileItemWriter<Calle> writer) {
        return new StepBuilder("exportStep").repository(jobRepository).<Calle, Calle>chunk(10).reader(reader)
                .processor(processor).writer(writer).faultTolerant().transactionManager(transactionManager).build();
    }
}
