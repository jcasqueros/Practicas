package com.viewnext.springbatchf.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;

public class ImportJob {

    public Job importJob(JobRepository jobRepository, JobListener listener, Step step1, Step step2) {
        return new JobBuilder("importUserJob").repository(jobRepository).incrementer(new RunIdIncrementer())
                .listener(listener).flow(step1).next(step2).end().build();
    }
}