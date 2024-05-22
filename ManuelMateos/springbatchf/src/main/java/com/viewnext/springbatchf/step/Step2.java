package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.job.JobSkipListener;
import com.viewnext.springbatchf.model.Calle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
public class Step2 {

    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            ItemReader<Calle> reader, ItemProcessor<Calle, Calle> processor, MongoItemWriter<Calle> writer,
            JobSkipListener jobSkipListener) {
        return new StepBuilder("step2").repository(jobRepository).<Calle, Calle>chunk(10).reader(reader)
                .processor(processor).writer(writer).faultTolerant().skipPolicy(new AlwaysSkipItemSkipPolicy())
                .listener(jobSkipListener).transactionManager(transactionManager).build();
    }
}

