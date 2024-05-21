package com.viewnext.springbatch.config;

import java.text.ParseException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.viewnext.springbatch.model.Direccion;
import com.viewnext.springbatch.step.chunk.DireccionItemProcess;
import com.viewnext.springbatch.step.chunk.DireccionItemReader;
import com.viewnext.springbatch.step.chunk.DireccionItemWriter;

@Configuration
@EnableAutoConfiguration
public class SpringBatchConfig {

	@Bean
	public DireccionItemReader itemReader() {
		return new DireccionItemReader();
	}
	
	@Bean
	public DireccionItemWriter itemWriter() {
		return new DireccionItemWriter();
	}
	
	@Bean
	public DireccionItemProcess itemProcessor() {
		return new DireccionItemProcess();
	}
	
	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(1);
		taskExecutor.setMaxPoolSize(5);
		taskExecutor.setQueueCapacity(50);
		return taskExecutor;
	}
	
	@Bean
	public Step readFile(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws ParseException {
		return new StepBuilder("readFile", jobRepository)
				.<Direccion, Direccion>chunk(100, transactionManager)
				.reader(itemReader())
				//.processor(itemProcessor)
				.writer(itemWriter())
				.faultTolerant()
				.skipLimit(100)
				.skip(FlatFileParseException.class)
				.taskExecutor(taskExecutor())
				.build();
	}
	
	@Bean(name= "firstBatchJob")
	public Job job(JobRepository jobRepository, @Qualifier("readFile") Step readFile) {
		return new JobBuilder("firstBatchJob", jobRepository)
				.start(readFile)
				.build();
	}

}
