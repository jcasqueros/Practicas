package com.viewnext.springbatchf.job;

import lombok.Data;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;

@Data
public class ImportUserJob {

    private JobRepository jobRepository;
    private JobListener listener;
    private Step step1;
    //private Step distritoCountStep;

    public Job importUserJob() {
        return new JobBuilder("importUserJob", jobRepository).incrementer(new RunIdIncrementer()).listener(listener)
                .flow(step1).end().build();
    }
}