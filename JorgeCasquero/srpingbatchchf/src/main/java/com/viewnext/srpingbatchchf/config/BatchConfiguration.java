package com.viewnext.srpingbatchchf.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.viewnext.srpingbatchchf.listener.LoggingSkipListener;
import com.viewnext.srpingbatchchf.model.TramoCalle;
import com.viewnext.srpingbatchchf.step.chunk.TramoCalleItemProcessor;
import com.viewnext.srpingbatchchf.validator.TramoCalleValidator;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	 @Autowired
	 private MongoTemplate mongoTemplate;

	@Bean
	public FlatFileItemReader<TramoCalle> reader() {

		return new FlatFileItemReaderBuilder<TramoCalle>().name("personItemReader")
				.resource(new ClassPathResource("/files/tramos_calle_BarrioDismuni.csv")).delimited().delimiter(",")
				.names("CODIGO_CALLE", "TIPO_VIA", "NOMBRE_CALLE", "PRIMER_NUM_TRAMO", "ULTIMO_NUM_TRAMO", "BARRIO",
						"COD_DISTRITO", "NOM_DISTRITO")
				.linesToSkip(1).fieldSetMapper(new BeanWrapperFieldSetMapper<TramoCalle>() {
					{
						setTargetType(TramoCalle.class);
					}
				}).build();
	}

	@Bean
	public TramoCalleItemProcessor processor() {
		return new TramoCalleItemProcessor(new TramoCalleValidator());
	}

	@Bean
	public MongoItemWriter<TramoCalle> writer(MongoTemplate mongoTemplate) {
		MongoItemWriter<TramoCalle> writer = new MongoItemWriter<>();
		writer.setTemplate(mongoTemplate);
		writer.setCollection("tramosCalles");
		return writer;
	}

	@Bean
	public Tasklet borrarBd() {
		return (contribution, chunkContext) -> {
			mongoTemplate.getDb().getCollection("tramosCalle").drop();
			return RepeatStatus.FINISHED;
		};
	}

	@Bean
	public Step clearDatabaseStep(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("borrarBd").tasklet(borrarBd()).build();
	}

	@Bean
	public Job importUserJob(JobBuilderFactory jobBuilderFactory, Step step1) {
		return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).flow(step1).end().build();

	}

	@Bean
	public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<TramoCalle> reader,
			ItemWriter<TramoCalle> writer, ItemProcessor<TramoCalle, TramoCalle> processor) {
		return stepBuilderFactory.get("step1").<TramoCalle, TramoCalle>chunk(10).reader(reader).processor(processor)
				.writer(writer).faultTolerant().skip(ValidationException.class)
				.skipPolicy(new AlwaysSkipItemSkipPolicy()).listener(new LoggingSkipListener()).build();
	}
}