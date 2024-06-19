package com.viewnext.springbatchf.step.listener;

import com.opencsv.CSVWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DistrictExportListenerTest {

    @Mock
    private CSVWriter districtCSVWriter;

    @Mock
    private StepExecution stepExecution;

    @InjectMocks
    private DistrictExportListener districtExportListener;

    @Test
    void beforeStep_WritesHeaderToCSV() throws IOException {
        // When
        districtExportListener.beforeStep(stepExecution);

        // Then
        verify(districtCSVWriter).writeNext(eq(new String[] { "NOM_DISTRITO", "NUM_CASAS" }), eq(false));
    }

    @Test
    void afterStep_ClosesCSVWriter() throws IOException {
        // When
        ExitStatus exitStatus = districtExportListener.afterStep(stepExecution);

        // Then
        verify(districtCSVWriter).close();
        assertEquals(ExitStatus.COMPLETED, exitStatus);
    }

    @Test
    void afterStep_ThrowsRuntimeException_OnIOException() throws IOException {
        // Given
        doThrow(new IOException()).when(districtCSVWriter).close();

        // When and Then
        assertThrows(RuntimeException.class, () -> districtExportListener.afterStep(stepExecution));
    }
}




