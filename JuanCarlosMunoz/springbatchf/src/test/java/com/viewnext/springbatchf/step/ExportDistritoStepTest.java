//package com.viewnext.springbatchf.step;
//
//import com.viewnext.springbatchf.step.chunk.export.district.ExportDistrictProcessor;
//import com.viewnext.springbatchf.step.chunk.export.district.ExportDistrictReader;
//import com.viewnext.springbatchf.step.chunk.export.district.ExportDistrictWriter;
//import com.viewnext.springbatchf.step.listener.DistrictExportListener;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import com.viewnext.springbatchf.config.SpringBatchConfig;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.test.JobRepositoryTestUtils;
//import org.springframework.batch.test.context.SpringBatchTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.batch.test.JobLauncherTestUtils;
//
//
//@SpringBatchTest
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {SpringBatchConfig.class})
//@TestPropertySource(properties = {
//        "execution =jobExportarCSV",
//        "filter=someFilterValue",
//        "log.distrito.file=Distrito.csv",
//        "log.tramos.file=tramosDB.csv",
//        "log.resource.folder=src/main/resources/files/",
//        "log.error.folder=src/main/resources/log/",
//        "log.resource.fileUser=classpath:files/ficherosUsuarios/*.csv",
//        "log.resource.finalFileUser=src/main/resources/files/ficherosFinales/",
//        "log.error.file=error_log.txt",
//        "csv.district.file=classpath:csv/tramos_calle_BarrioDismuni.csv",
//        "csv.multi.thread.csv.file=classpath:csv/tramos_calle_BarrioDismuniOneMillion.csv"
//})
//class ExportDistritoStepTest {
//
//    @InjectMocks
//    private ExportDistritoStep exportDistritoStep;
//
//    @Mock
//    private ExportDistrictReader reader;
//    @Mock
//    private ExportDistrictProcessor processor;
//    @Mock
//    private ExportDistrictWriter write;
//    @Mock
//    private StepBuilderFactory stepBuilderFactory;
//    @Mock
//    private DistrictExportListener districtExportListener;
//
//
//    @Test
//    void testExportChunkStep() {
//
//        exportDistritoStep.exportChunkStep(reader,processor, write,stepBuilderFactory,districtExportListener);
//
//    }
//}
//
//
