package com.viewnext.springbatchf.job;

import lombok.Data;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;

@Data
public class DistritoJob {

    private JobRepository jobRepository;
    private JobListener listener;
    private Step step2;

    public Job distritoJob() {
        return new JobBuilder("distritoUserJob", jobRepository).incrementer(new RunIdIncrementer()).listener(listener)
                .flow(step2).end().build();
    }
}