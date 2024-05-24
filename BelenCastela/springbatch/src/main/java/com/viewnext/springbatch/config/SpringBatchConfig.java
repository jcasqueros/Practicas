package com.viewnext.springbatch.config;

import java.text.ParseException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.viewnext.springbatch.model.Direccion;
import com.viewnext.springbatch.step.chunk.DireccionItemProcess;
import com.viewnext.springbatch.step.chunk.DireccionItemReader;
import com.viewnext.springbatch.step.chunk.DireccionItemWriter;
import com.viewnext.springbatch.step.chunk.DistritoCounterItemProcessor;
import com.viewnext.springbatch.step.chunk.FlatFileParseExceptionSkipListener;
import com.viewnext.springbatch.step.chunk.DatabaseToCSV;

@Configuration
@EnableAutoConfiguration
public class SpringBatchConfig {

	@Bean
	public DireccionItemReader itemReaderDireccion() {
		return new DireccionItemReader();
	}
	
	@Bean
	public DireccionItemWriter itemWriterDireccion() {
		return new DireccionItemWriter();
	}
	
	@Bean
	public DireccionItemProcess itemProcessorDireccion() {
		return new DireccionItemProcess();
	}
	
	@Bean
	public DistritoCounterItemProcessor itemProcessorCounterDistrito() {
		return new DistritoCounterItemProcessor();
	}
	
	/*@Bean
	public DatabaseToCSV csvItemWriter() {
		return new DatabaseToCSV();
	}*/
	
	
	//MOVE TO DatabaseToCSV ----->
	 @Bean
	    public FlatFileItemWriter<Direccion> csvItemWriter() {
	        FlatFileItemWriter<Direccion> writer = new FlatFileItemWriter<>();
	        writer.setResource(new FileSystemResource("src/main/resources/WrittenDownH2.csv"));
	        writer.setLineAggregator(new DelimitedLineAggregator<Direccion>() {
	            {
	                setDelimiter(",");
	                setFieldExtractor(new BeanWrapperFieldExtractor<Direccion>() {
	                    {
	                        setNames(new String[] {"codigoCalle", "tipoVia", "nombreCalle", "primerNumTramo", "ultimoNumTramo", "barrio", "codDistrito", "nomDistrito"});
	                    }
	                });
	            }
	        });
	        return writer;
	    }

	
	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(1);
		//Riesgo registros duplicados, ojo
		taskExecutor.setMaxPoolSize(1);
		taskExecutor.setQueueCapacity(1);
		return taskExecutor;
	}
	
	@Bean
	public Step readFile(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws ParseException {
		return new StepBuilder("readFile", jobRepository)
				.<Direccion, Direccion>chunk(1000, transactionManager)
				.reader(itemReaderDireccion())
				.processor(itemProcessorCounterDistrito())
				.processor(itemProcessorDireccion())
				.writer(csvItemWriter())
				.writer(itemWriterDireccion())
				.taskExecutor(taskExecutor())
				.faultTolerant()
				//.skipLimit(15)
				.skipPolicy(new AlwaysSkipItemSkipPolicy())
				.skip(FlatFileParseException.class)
				.listener(new FlatFileParseExceptionSkipListener())
				.build();
	}
	
	@Bean(name= "firstBatchJob")
	public Job job(JobRepository jobRepository, @Qualifier("readFile") Step readFile) {
		return new JobBuilder("firstBatchJob", jobRepository)
				.start(readFile)
				.build();
	}

}
