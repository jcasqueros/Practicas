package com.viewnext.springbatch.step;

import com.viewnext.springbatch.job.JobSkipListener;
import com.viewnext.springbatch.model.Calle;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

public class MultiThreadStep {
    public Step customStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            FlatFileItemReader<Calle> reader, MongoItemWriter<Calle> writer, JobSkipListener skipListener,
            TaskExecutor taskExecutor) {
        return new StepBuilder("step1").repository(jobRepository).<Calle, Calle>chunk(10).reader(reader).writer(writer)
                .faultTolerant().listener(skipListener).skipPolicy(new AlwaysSkipItemSkipPolicy())
                .transactionManager(transactionManager).taskExecutor(taskExecutor).build();
    }
}
