package com.viewnext.springbatchf.config;

import com.viewnext.springbatchf.job.ImportUserJob;
import com.viewnext.springbatchf.job.JobListener;
import com.viewnext.springbatchf.job.MongoDataSource;
import com.viewnext.springbatchf.model.Calle;
import com.viewnext.springbatchf.step.CalleReader;
import com.viewnext.springbatchf.step.DistritoProcessor;
import com.viewnext.springbatchf.step.DistritoWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@EnableAutoConfiguration
@Configuration
public class ApplicationAutoConfig {

    @Value("${file.input}")
    private String fileInput;

    @Value("${distrito}")
    String distrito;

    @Bean
    public MongoDataSource mongoDataSource() {
        return new MongoDataSource();
    }

    @Bean
    public ItemProcessor<Calle, Calle> distritoProcessor(@Value("${distrito}") String distrito) {
        DistritoProcessor processor = new DistritoProcessor();
        processor.setDistrito(distrito);
        return processor;
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, JobListener listener, @Qualifier("step1") Step step1) {
        ImportUserJob importUserJob = new ImportUserJob();
        importUserJob.setJobRepository(jobRepository);
        importUserJob.setListener(listener);
        importUserJob.setStep1(step1);
        //importUserJob.setDistritoCountStep(distritoCountStep);
        return importUserJob.importUserJob();
    }

    @Bean
    public FlatFileItemReader reader() {
        return new CalleReader().reader(fileInput);
    }

    //    @Bean
    //    public MongoPagingItemReader<Calle> mongoItemReader(MongoTemplate mongoTemplate) {
    //        return new MongoReader().reader();
    //    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            MongoDataSource mongoTemplate) {
        return new StepBuilder("step1", jobRepository).<Calle, Calle>chunk(10, transactionManager).reader(reader())
                .processor(distritoProcessor(distrito)).writer(distritoWriter(mongoTemplate.getMongoTemplate()))
                .faultTolerant().listener(new SkipListener<Calle, Calle>() {
                    @Override
                    public void onSkipInRead(Throwable t) {
                        log.error("Error al leer línea: {}", t.getMessage());
                    }
                }).skipPolicy(new AlwaysSkipItemSkipPolicy()).build();
    }

    //    @Bean
    //    public Step distritoCountStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
    //            MongoTemplate mongoTemplate) {
    //        return new StepBuilder("distritoCountStep", jobRepository).<Calle, Calle>chunk(10, transactionManager)
    //                .reader(reader()).writer(distritoCountWriter(mongoTemplate)).faultTolerant()
    //                .listener(new SkipListener<Calle, Calle>() {
    //                    @Override
    //                    public void onSkipInRead(Throwable t) {
    //                    }
    //                }).skipPolicy(new AlwaysSkipItemSkipPolicy()).build();
    //    }

    //    @Bean
    //    public Step step3(JobRepository jobRepository, PlatformTransactionManager transactionManager,
    //            MongoTemplate mongoTemplate){
    //        return new StepBuilder("step3", jobRepository).chunk(10, transactionManager).reader(mongoItemReader(mongoTemplate)).writer(csvWriter())
    //    }

    @Bean
    public DistritoWriter distritoWriter(MongoTemplate mongoTemplate) {
        return new DistritoWriter(mongoTemplate);
    }

    //    @Bean
    //    public DistritoCountWriter distritoCountWriter(MongoTemplate mongoTemplate) {
    //        return new DistritoCountWriter(mongoTemplate);
    //    }

    //    @Bean
    //    public ItemWriter<Calle> csvWriter() {
    //        return new CsvWriter().csvWriter();
    //    }
}