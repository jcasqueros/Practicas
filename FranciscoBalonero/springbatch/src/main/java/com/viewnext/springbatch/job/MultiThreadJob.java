package com.viewnext.springbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;

public class MultiThreadJob {
    public Job importCalleMultiThread(JobRepository jobRepository, DefaultJobListener listener, Step step1) {
        return new JobBuilder("MultiThreadJob").repository(jobRepository).incrementer(new RunIdIncrementer())
                .listener(listener).flow(step1).end().build();
    }
}
