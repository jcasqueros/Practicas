package com.viewnext.springbatchf.config;

import com.opencsv.CSVWriter;
import com.viewnext.springbatchf.job.StreetExportJob;
import com.viewnext.springbatchf.job.StreetImportJob;
import com.viewnext.springbatchf.model.entity.Street;
import com.viewnext.springbatchf.step.ImportStep;
import com.viewnext.springbatchf.step.ExportDistritoStep;
import com.viewnext.springbatchf.step.ExportTramoStep;
import com.viewnext.springbatchf.step.chunk.ImportStreetReader;
import com.viewnext.springbatchf.step.chunk.export.tramo.ExportTramoProcessor;
import com.viewnext.springbatchf.step.chunk.export.tramo.ExportTramoReader;
import com.viewnext.springbatchf.step.chunk.export.tramo.ExportTramoWriter;
import com.viewnext.springbatchf.step.chunk.export.district.ExportDistrictProcessor;
import com.viewnext.springbatchf.step.chunk.export.district.ExportDistrictReader;
import com.viewnext.springbatchf.step.chunk.export.district.ExportDistrictWriter;
import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetProcessor;
import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetWriter;
import com.viewnext.springbatchf.step.listener.DistrictExportListener;
import com.viewnext.springbatchf.step.listener.StreetImportListener;

import com.viewnext.springbatchf.step.listener.TramoExportListener;
import com.viewnext.springbatchf.step.skippolicy.StreetSkipPolicy;
import com.viewnext.springbatchf.util.exception.GenericException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;

import org.springframework.core.io.ResourceLoader;

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

    private static final String DISTRITO_FILE = "Distritos.csv";
    private static final String TRAMOS_FILE = "tramosDB.csv";
    private static final String RESOURCE_FOLDER = "src/main/resources/files/";
    private static final String ERROR_FOLDER = "src/main/resources/log/";
    private static final String ERROR_FILE = "error_log.txt";

    /**
     * Job.
     *
     * @param jobBuilderFactory
     *         the job builder factory
     * @param importSteep
     *         the import steep
     * @param exportTramoStep
     *         the export tramo step
     * @param exportDistrictStep
     *         the export district step
     * @return the job
     * @throws GenericException
     *         the generic exception
     */
    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, @Qualifier("importSteep") Step importSteep,
            @Qualifier("exportTramoStep") Step exportTramoStep,
            @Qualifier("exportDistrictStep") Step exportDistrictStep) throws GenericException {

        if (execution.equals("jobExportarCSV")) {
            var job = new StreetExportJob();
            return job.job(jobBuilderFactory, exportTramoStep, exportDistrictStep);
        } else if (execution.equals("jobImportarDB")) {
            var job = new StreetImportJob();
            return job.job(jobBuilderFactory, importSteep);
        }
        throw new GenericException("Invalid value provided for the 'execution' property");
    }

    /**
     * Import steep.
     *
     * @param customerItemReader
     *         the customer item reader
     * @param processor
     *         the processor
     * @param writer
     *         the writer
     * @param stepBuilderFactory
     *         the step builder factory
     * @param streetImportListener
     *         the street import listener
     * @param streetSkipPolicy
     *         the street skip policy
     * @return the step
     */
    @Bean
    public Step importSteep(FlatFileItemReader<Street> customerItemReader, ImportStreetProcessor processor,
            ImportStreetWriter writer, StepBuilderFactory stepBuilderFactory, StreetImportListener streetImportListener,
            StreetSkipPolicy streetSkipPolicy) {

        var varStreetChunkStep = new ImportStep();

        return varStreetChunkStep.chunkStep(customerItemReader, processor, writer, stepBuilderFactory,
                streetImportListener, streetSkipPolicy);
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

    /**
     * Export tramo step step.
     *
     * @param reader
     *         the reader
     * @param processor
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
     * @param resourceLoader
     *         the resource loader
     * @return the flat file item reader
     */
    @Bean
    public FlatFileItemReader<Street> reader(@Qualifier("webApplicationContext") ResourceLoader resourceLoader) {
        var reader = new ImportStreetReader();
        return reader.reader(resourceLoader);
    }

    /**
     * Writer import street writer.
     *
     * @return the import street writer
     */
    @Bean
    public ImportStreetWriter writer() {
        return new ImportStreetWriter();
    }

    /**
     * Processor import street processor.
     *
     * @return the import street processor
     */
    @Bean
    public ImportStreetProcessor processor() {
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
        return new CSVWriter(new FileWriter(RESOURCE_FOLDER + DISTRITO_FILE));
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
        return new CSVWriter(new FileWriter(RESOURCE_FOLDER + TRAMOS_FILE));
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
        return new FileWriter(ERROR_FOLDER + ERROR_FILE);
    }

}
