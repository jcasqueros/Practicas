package com.viewnext.batch01.config.job;

import com.viewnext.batch01.config.step.StepConfig;
import com.viewnext.batch01.job.history.DistrictFilterHistoryEntry;
import com.viewnext.batch01.job.listener.DistrictFilterJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static com.viewnext.batch01.util.InjectablePlaceholders.FILTER_HISTORY_WRITER_PLACEHOLDER;
import static com.viewnext.batch01.util.InjectablePlaceholders.STRING_PLACEHOLDER;

/**
 * Configuration for batch jobs.
 *
 * @author Antonio Gil
 */
@EnableBatchProcessing
@Configuration
@Import({StepConfig.class})
public class JobConfig {

    private final JobBuilderFactory jobs;

    public JobConfig(JobBuilderFactory jobs) {
        this.jobs = jobs;
    }

    @Bean
    @JobScope
    public DistrictFilterJobListener filterJobListener(MongoItemWriter<DistrictFilterHistoryEntry> writer,
                                                       @Value("#{jobParameters['batch01.district_filter.district']}")
                                                       String district) {
        return new DistrictFilterJobListener(writer, district);
    }

    @Bean
    public Job districtFilterJob(@Qualifier("districtFilterStep") Step districtFilterStep) {
        final String jobName = "district_filter";
        return jobs.get(jobName)
                .start(districtFilterStep)
                .listener(filterJobListener(FILTER_HISTORY_WRITER_PLACEHOLDER, STRING_PLACEHOLDER))
                .build();
    }

    @Bean
    public Job dbDumpJob(@Qualifier("dumpDistrictsToCsvStep") Step dumpDistrictsToCsvStep,
                         @Qualifier("dumpFilterHistoryEntriesToCsvStep") Step dumpFilterHistoryEntriesToCsvStep) {
        final String jobName = "db_dump";

        return jobs.get(jobName)
                .start(dumpDistrictsToCsvStep)
                .next(dumpFilterHistoryEntriesToCsvStep)
                .build();
    }

}
