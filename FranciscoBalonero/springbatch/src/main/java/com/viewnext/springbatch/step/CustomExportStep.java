package com.viewnext.springbatch.step;

import com.viewnext.springbatch.model.Calle;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.transaction.PlatformTransactionManager;

public class CustomExportStep {
    public Step customStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            MongoItemReader<Calle> reader, ItemProcessor<Calle, Calle> processor, FlatFileItemWriter<Calle> writer) {
        return new StepBuilder("step1").repository(jobRepository).<Calle, Calle>chunk(10).reader(reader)
                .processor(processor).writer(writer).faultTolerant().transactionManager(transactionManager).build();
    }
}
