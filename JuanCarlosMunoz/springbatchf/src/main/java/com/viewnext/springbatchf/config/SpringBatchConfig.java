package com.viewnext.springbatchf.config;

import com.opencsv.CSVWriter;
import com.viewnext.springbatchf.job.*;
import com.viewnext.springbatchf.step.*;
import com.viewnext.springbatchf.step.chunk.export.tramo.ExportTramoProcessor;
import com.viewnext.springbatchf.step.chunk.export.tramo.ExportTramoReader;
import com.viewnext.springbatchf.step.chunk.export.tramo.ExportTramoWriter;
import com.viewnext.springbatchf.step.chunk.export.district.ExportDistrictProcessor;
import com.viewnext.springbatchf.step.chunk.export.district.ExportDistrictReader;
import com.viewnext.springbatchf.step.chunk.export.district.ExportDistrictWriter;
import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetProcessor;
import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetReader;
import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetWriter;
import com.viewnext.springbatchf.step.chunk.writedatabase.WriteDataBaseWriter;
import com.viewnext.springbatchf.step.chunk.writefile.WriteFileProcessor;
import com.viewnext.springbatchf.step.chunk.writefile.WriteFileReader;
import com.viewnext.springbatchf.step.chunk.writefile.WriteFileWriter;
import com.viewnext.springbatchf.step.listener.DistrictExportListener;
import com.viewnext.springbatchf.step.listener.StreetImportListener;

import com.viewnext.springbatchf.step.listener.TramoExportListener;
import com.viewnext.springbatchf.step.skippolicy.StreetSkipPolicy;
import com.viewnext.springbatchf.util.exception.GenericException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;

import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.task.AsyncTaskExecutor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.FileWriter;
import java.io.IOException;

/**
 * The type Spring batch config.
 */
@Configuration
@EnableAutoConfiguration
public class SpringBatchConfig {

    /**
     * The Execution.
     */
    @Value("${execution}")
    String execution;

    @Value("${log.distrito.file}")
    private String distritoFile;

    @Value("${log.tramos.file}")
    private String tramosFile;

    @Value("${log.resource.folder}")
    private String resourceFolder;

    @Value("${log.error.folder}")
    private String errorFolder;

    @Value("${log.error.file}")
    private String errorFile;

    @Value("${csv.district.file}")
    private String csvPathDistrict;

    @Value("${csv.multi.thread.csv.file}")
    private String csvPathAllDistrict;

    @Value("${log.resource.fileUser}")
    private String filPathUser;

    /**
     * Job.
     *
     * @param jobBuilderFactory
     *         the job builder factory
     * @param importStep
     *         the import steep
     * @param exportTramoStep
     *         the export tramo step
     * @param exportDistrictStep
     *         the export district step
     * @param multiThreadStep
     *         the multi thread step
     * @return the job
     * @throws GenericException
     *         the generic exception
     */
    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, @Qualifier("importStep") Step importStep,
            @Qualifier("exportTramoStep") Step exportTramoStep,
            @Qualifier("exportDistrictStep") Step exportDistrictStep,
            @Qualifier("multiThreadStep") Step multiThreadStep,
            @Qualifier("writerStep") Step writerStep,
            @Qualifier("writeDbStep") Step writeDbStep)
             throws GenericException {

        Job returnJob;
        OneStepJob oneStepJob;
        TwoStepJob twoStepJob;

        switch (execution) {
        case "jobExportarCSV":

            twoStepJob = new TwoStepJob();
            returnJob = twoStepJob.job(jobBuilderFactory, exportTramoStep, exportDistrictStep, "streetExportJob");

            break;
        case "jobImportarDB":
            oneStepJob = new OneStepJob();
            returnJob = oneStepJob.job(jobBuilderFactory, importStep, "streetImportJob");
            break;
        case "jobParaleloWrite":
            var job = new ParallelStepJob();
            returnJob = job.job(jobBuilderFactory, writerStep,writeDbStep);
            break;

        default:
            oneStepJob = new OneStepJob();
            returnJob = oneStepJob.job(jobBuilderFactory, multiThreadStep, "multiThreadsJob");
            break;

        }
        return returnJob;

    }

    @Bean
    public Step writeDbStep(StepBuilderFactory stepBuilderFactory,  WriteDataBaseWriter writer)
            throws IOException {

        var step = new WriteToDatabaseStep();

        return step.writeDbStep(writeFileReader(), writer, stepBuilderFactory);
    }

    @Bean
    public Step writerStep(StepBuilderFactory stepBuilderFactory, WriteFileProcessor processor, WriteFileWriter writer)
            throws IOException {

        var step = new WriteFileStep();

        return step.writeFile(writeFileReader(), processor, writer, stepBuilderFactory);
    }

    /**
     * Import steep.
     *
     * @param stepBuilderFactory
     *         the step builder factory
     * @param streetImportListener
     *         the street import listener
     * @param streetSkipPolicy
     *         the street skip policy
     * @param resourceLoader
     *         the resource loader
     * @return the step
     */
    @Bean
    public Step importStep(StepBuilderFactory stepBuilderFactory, StreetImportListener streetImportListener,
            StreetSkipPolicy streetSkipPolicy, @Qualifier("webApplicationContext") ResourceLoader resourceLoader) {

        Resource resource = resourceLoader.getResource(csvPathDistrict);

        var varStreetChunkStep = new ImportStep();

        return varStreetChunkStep.importStep(importStreetReader(), importStreetProcessor(), importStreetWriter(),
                stepBuilderFactory, streetImportListener, streetSkipPolicy, resource);
    }

    /**
     * Export district step step.
     *
     * @param reader
     *         the reader
     * @param processor
     *         the processor
     * @param writer
     *         the writer
     * @param stepBuilderFactory
     *         the step builder factory
     * @param districtExportListener
     *         the district export listener
     * @return the step
     */
    @Bean
    public Step exportDistrictStep(ExportDistrictReader reader, ExportDistrictProcessor processor,
            ExportDistrictWriter writer, StepBuilderFactory stepBuilderFactory,
            DistrictExportListener districtExportListener) {

        var varStreetStep = new ExportDistritoStep();

        return varStreetStep.exportChunkStep(reader, processor, writer, stepBuilderFactory, districtExportListener);
    }
    @Bean
    public WriteDataBaseWriter writeDataBaseWriter() {
        return new WriteDataBaseWriter();
    }



    /**
     * Multi thread step step.
     *
     * @param stepBuilderFactory
     *         the step builder factory
     * @param streetImportListener
     *         the street import listener
     * @param streetSkipPolicy
     *         the street skip policy
     * @param resourceLoader
     *         the resource loader
     * @return the step
     */
    @Bean
    public Step multiThreadStep(StepBuilderFactory stepBuilderFactory, StreetImportListener streetImportListener,
            StreetSkipPolicy streetSkipPolicy, @Qualifier("webApplicationContext") ResourceLoader resourceLoader) {

        Resource resource = resourceLoader.getResource(csvPathAllDistrict);

        var step = new MultiThreadedStep();

        return step.multiThreadStep(importStreetReader(), importStreetWriter(), stepBuilderFactory,
                streetImportListener, streetSkipPolicy, resource, asyncTaskExecutor());
    }

    /**
     * Export tramo step step.
     *
     * @param reader
     *         the reader
     * @param processor+
     *         the processor
     * @param writer
     *         the writer
     * @param stepBuilderFactory
     *         the step builder factory
     * @param tramoExportListener
     *         the tramo export listener
     * @return the step
     */
    @Bean
    public Step exportTramoStep(ExportTramoReader reader, ExportTramoProcessor processor,
            @Qualifier("exportTramoWriter") ExportTramoWriter writer, StepBuilderFactory stepBuilderFactory,
            TramoExportListener tramoExportListener) {

        var varStreetExportStep = new ExportTramoStep();
        return varStreetExportStep.exportChunkStep(reader, processor, writer, stepBuilderFactory, tramoExportListener);
    }

    /**
     * Reader flat file item reader.
     *
     * @return the flat file item reader
     */
    @Bean
    public ImportStreetReader importStreetReader() {
        return new ImportStreetReader();
    }

    /**
     * Writer import street writer.
     *
     * @return the import street writer
     */
    @Bean
    public ImportStreetWriter importStreetWriter() {
        return new ImportStreetWriter();
    }

    /**
     * Processor import street processor.
     *
     * @return the import street processor
     */
    @Bean
    public ImportStreetProcessor importStreetProcessor() {
        return new ImportStreetProcessor();
    }

    /**
     * District csv writer.
     *
     * @return the csv writer
     * @throws IOException
     *         the io exception
     */
    @Bean
    public CSVWriter districtCSVWriter() throws IOException {
        return new CSVWriter(new FileWriter(resourceFolder + distritoFile));
    }

    /**
     * Tramo csv writer.
     *
     * @return the csv writer
     * @throws IOException
     *         the io exception
     */
    @Bean
    public CSVWriter tramoCSVWriter() throws IOException {
        return new CSVWriter(new FileWriter(resourceFolder + tramosFile));
    }

    /**
     * Log writer file writer.
     *
     * @return the file writer
     * @throws IOException
     *         the io exception
     */
    @Bean
    public FileWriter logWriter() throws IOException {
        return new FileWriter(errorFolder + errorFile);
    }

    /**
     * Async task executor async task executor.
     *
     * @return the async task executor
     */
    @Bean
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        return executor;
    }

    @Bean
    public MultiResourceItemReader<Object> writeFileReader() throws IOException {
        MultiResourceItemReader<Object> reader = new MultiResourceItemReader<>();
        reader.setResources(new PathMatchingResourcePatternResolver().getResources(filPathUser));
        reader.setDelegate(new WriteFileReader());
        return reader;
    }


    @Bean
    public Partitioner partitioner() {
        return new MultiResourcePartitioner();
    }
}
