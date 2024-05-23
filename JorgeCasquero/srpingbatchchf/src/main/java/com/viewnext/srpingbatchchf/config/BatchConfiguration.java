package com.viewnext.srpingbatchchf.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.viewnext.srpingbatchchf.step.tasklet.ItemProcessorStep;
import com.viewnext.srpingbatchchf.step.tasklet.ItemReaderStep;
import com.viewnext.srpingbatchchf.step.tasklet.ItemWriterStep;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	@JobScope // este paso solo esta disponible en el job(solo cuando se utilize springBatch)
	public ItemReaderStep itemReaderStep() {
		return new ItemReaderStep();
	}

	@Bean
	@JobScope
	public ItemProcessorStep itemProcessorStep() {
		return new ItemProcessorStep();
	}

	@Bean
	@JobScope
	public ItemWriterStep itemWriterStep() {
		return new ItemWriterStep();
	}
	
	
	@Bean
	public Step readFileStep() {
		return stepBuilderFactory.get("itemReaderStep")
				.tasklet(itemReaderStep()).build();
	}
	
	@Bean
	public Step processDataStep() {
		return stepBuilderFactory.get("itemProcessorStep")
				.tasklet(itemProcessorStep()).build();
	}
	@Bean
	public Step writeFileStep() {
		return stepBuilderFactory.get("itemWriterStep")
				.tasklet(itemWriterStep()).build();
	}
	
	@Bean
	public Job readCsv() {
		return jobBuilderFactory.get("readCsvJob").
				start(readFileStep())
				.next(processDataStep())
				.next(writeFileStep())
				.build();
		
	}
}
