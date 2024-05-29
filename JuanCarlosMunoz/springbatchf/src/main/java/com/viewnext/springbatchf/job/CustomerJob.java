package com.viewnext.springbatchf.job;


import org.springframework.batch.core.Step;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;




public class CustomerJob {

    public Job job(JobBuilderFactory jobBuilderFactory,
            Step streetChunkStep) {


        return jobBuilderFactory.get("customerJob")
                //For each time we launch a job, we increment the ID
                .incrementer(new RunIdIncrementer())
                //First step that will be executed
                .flow(streetChunkStep)
//                .next(chunkStep)
                .end()
                .build();
    }

}
