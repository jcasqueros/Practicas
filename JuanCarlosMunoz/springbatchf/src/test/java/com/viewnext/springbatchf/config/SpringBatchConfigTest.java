//package com.viewnext.springbatchf.config;
//
//import com.viewnext.springbatchf.step.chunk.writedatabase.WriteDataBaseWriter;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.task.AsyncTaskExecutor;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.junit.Assert.assertNotNull;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class SpringBatchConfigTest {
//
//    @Autowired
//    private SpringBatchConfig springBatchConfig;
//
//    @Test
//    public void testJob() {
//        Job job = springBatchConfig.job();
//        assertNotNull(job);
//    }
//
//    @Test
//    public void testWriteDbStep() {
//        Step writeDbStep = springBatchConfig.writeDbStep();
//        assertNotNull(writeDbStep);
//    }
//
//    @Test
//    public void testWriterStep() {
//        Step writerStep = springBatchConfig.writerStep();
//        assertNotNull(writerStep);
//    }
//
//    @Test
//    public void testImportStep() {
//        Step importStep = springBatchConfig.importStep();
//        assertNotNull(importStep);
//    }
//
//    @Test
//    public void testMultiThreadStep() {
//        Step multiThreadStep = springBatchConfig.multiThreadStep();
//        assertNotNull(multiThreadStep);
//    }
//
//    @Test
//    public void testExportTramoStep() {
//        Step exportTramoStep = springBatchConfig.exportTramoStep();
//        assertNotNull(exportTramoStep);
//    }
//
//    @Test
//    public void testWriteDataBaseWriter() {
//        WriteDataBaseWriter writeDataBaseWriter = springBatchConfig.writeDataBaseWriter();
//        assertNotNull(writeDataBaseWriter);
//    }
//
//    @Test
//    public void testAsyncTaskExecutor() {
//        AsyncTaskExecutor asyncTaskExecutor = springBatchConfig.asyncTaskExecutor();
//        assertNotNull(asyncTaskExecutor);
//    }
//}
