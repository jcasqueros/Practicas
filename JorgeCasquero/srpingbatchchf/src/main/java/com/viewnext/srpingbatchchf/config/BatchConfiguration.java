package com.viewnext.srpingbatchchf.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.viewnext.srpingbatchchf.model.TramoCalle;
import com.viewnext.srpingbatchchf.step.chunk.ChunkItemReader;
import com.viewnext.srpingbatchchf.step.chunk.ChunkItemWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	private final JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

    BatchConfiguration(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

	@Bean
	public ChunkItemReader itemReader() {
		return new ChunkItemReader();
	}

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(1);
		taskExecutor.setMaxPoolSize(5);
		taskExecutor.setQueueCapacity(5);

		return taskExecutor;
	}

	@Bean
	public ChunkItemWriter itemWriter() {
		return new ChunkItemWriter();
	}

//    @Bean
//    @StepScope
//    public TramoDistrictFilterer tramoFilterer
//            (@Value("#{jobParameters['batch01.district_filter.district']}") String districtName) {
//        return new TramoDistrictFilterer(districtName);
//        
//    }
	// en chunknuestros pasos estan en un solo paso

	@Bean
	public Step districtFilterStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return stepBuilderFactory.get("district_filter-step01-district_filter").repository(jobRepository)
				.<TramoCalle, TramoCalle>chunk(16).faultTolerant().skip(FlatFileParseException.class)
				.skipPolicy(new AlwaysSkipItemSkipPolicy())
//                .processor(tramoFilterer(null)
				.writer(itemWriter()).transactionManager(transactionManager).build();
	}

	@Bean
	public Job districtFilterJob(Step districtFilterStep) {
		return jobBuilderFactory.get("district_filter").start(districtFilterStep)
				.build();
	}

}

/*
 * @Autowired public JobBuilderFactory jobBuilderFactory;
 * 
 * @Autowired public StepBuilderFactory stepBuilderFactory;
 * 
 * @Bean
 * 
 * @JobScope // este paso solo esta disponible en el job(solo cuando se utilize
 * springBatch) public ItemReaderStep itemReaderStep() { return new
 * ItemReaderStep(); }
 * 
 * @Bean
 * 
 * @JobScope public ItemProcessorStep itemProcessorStep() { return new
 * ItemProcessorStep(); }
 * 
 * @Bean
 * 
 * @JobScope public ItemWriterStep itemWriterStep() { return new
 * ItemWriterStep(); }
 * 
 * 
 * @Bean public Step readFileStep() { return
 * stepBuilderFactory.get("itemReaderStep") .tasklet(itemReaderStep()).build();
 * }
 * 
 * @Bean public Step processDataStep() { return
 * stepBuilderFactory.get("itemProcessorStep")
 * .tasklet(itemProcessorStep()).build(); }
 * 
 * @Bean public Step writeFileStep() { return
 * stepBuilderFactory.get("itemWriterStep") .tasklet(itemWriterStep()).build();
 * }
 * 
 * @Bean public Job readCsv() { return jobBuilderFactory.get("readCsvJob").
 * start(readFileStep()) .next(processDataStep()) .next(writeFileStep())
 * .build();
 * 
 * }
 */
//}
