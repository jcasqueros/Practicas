package com.viewnext.springbatchf.config;

import com.viewnext.springbatchf.model.TramoCalle;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class StepConfig {

    private final ReaderConfig readerConfig;

    private final ProcessorConfig processorConfig;

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            JpaItemWriter writer) {
        return new StepBuilder("step1", jobRepository).<TramoCalle, TramoCalle>chunk(10, transactionManager)
                .reader(readerConfig.reader()).processor(processorConfig.processor()).writer(writer).build();
    }
}
