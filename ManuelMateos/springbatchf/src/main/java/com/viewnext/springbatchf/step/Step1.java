package com.viewnext.springbatchf.step;

import com.viewnext.springbatchf.model.Calle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
public class Step1 {

    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            ItemReader<Calle> reader, ItemProcessor<Calle, Calle> processor, ItemWriter<Calle> writer) {
        return new StepBuilder("step1", jobRepository).<Calle, Calle>chunk(10, transactionManager).reader(reader)
                .processor(processor).writer(writer).faultTolerant().listener(new SkipListener<Calle, Calle>() {
                    @Override
                    public void onSkipInRead(Throwable t) {
                        log.error("Error al leer línea: {}", t.getMessage());
                    }
                }).skipPolicy(new AlwaysSkipItemSkipPolicy()).build();
    }
}

