package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.job.JobSkipListener;
import com.viewnext.springbatchf.model.Calle;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.transaction.PlatformTransactionManager;

public class Step1 {

    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            ItemReader<Calle> reader, ItemProcessor<Calle, Calle> processor, MongoItemWriter<Calle> writer,
            JobSkipListener jobSkipListener) {
        return new StepBuilder("step1").repository(jobRepository).<Calle, Calle>chunk(10).reader(reader)
                .processor(processor).writer(writer).faultTolerant().listener(jobSkipListener)
                .skipPolicy(new AlwaysSkipItemSkipPolicy()).transactionManager(transactionManager).build();
    }
}