package com.viewnext.springbatchf.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * The type Parallel step job.
 */
public class ParallelStepJob {

    /**
     * Task executor simple async task executor.
     *
     * @return the simple async task executor
     */
    @Bean
    public SimpleAsyncTaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    /**
     * Job job.
     *
     * @param jobBuilderFactory
     *         the job builder factory
     * @param writeFileStep
     *         the write file step
     * @param writeDbStep
     *         the write db step
     * @return the job
     */
    public Job job(JobBuilderFactory jobBuilderFactory, Step writeFileStep, Step writeDbStep) {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow1");
        Flow flow1 = flowBuilder.start(writeFileStep).build();
        FlowBuilder<Flow> flowBuilder2 = new FlowBuilder<>("flow2");
        Flow flow2 = flowBuilder2.start(writeDbStep).build();

        return jobBuilderFactory.get("parallelJob").incrementer(new RunIdIncrementer()).start(flow1)
                .split(taskExecutor()).add(flow2).end().build();
    }
}
