package com.viewnext.springbatch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.viewnext.springbatch.model.Direccion;

@Configuration
@EnableAutoConfiguration
public class SpringBatchConfig {

	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ItemReader<Direccion> itemReader, ItemProcessor<Direccion, Direccion> itemProcessor, ItemWriter<Direccion> itemWriter) {

		Step step = stepBuilderFactory.get("ELT-file-load")
				.<Direccion,Direccion>chunk(100)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();

		return jobBuilderFactory.get("ETL-Load")
				.incrementer(new RunIdIncrementer())
				.start(step)
				.build();
	}
	
	@Bean
	public FlatFileItemReader<Direccion> fileItemReader(@Value("${input}") Resource resource) {
		FlatFileItemReader<Direccion> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setName("CSV Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}
	
	@Bean
	public LineMapper<Direccion> lineMapper() {
		DefaultLineMapper<Direccion> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		
		lineTokenizer .setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] {"ID", "CODIGO_CALLE", "TIPO_VIA", "NOMBRE_CALLE", "PRIMER_NUM_TRAMO", "ULTIMO_NUM_TRAMO", "BARRIO", "COD_DISTRITO", "NOM_DISTRITO"});
		
		BeanWrapperFieldSetMapper<Direccion> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		
		return null;
	}
}

//19:37 cvs to database
