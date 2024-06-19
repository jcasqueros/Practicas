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
class TramoExportListenerTest {

    @Mock
    private CSVWriter tramoCSVWriter;

    @InjectMocks
    private TramoExportListener tramoExportListener;

    @Test
    void beforeStep_WritesHeaderToCSV() {
        // Given
        StepExecution stepExecution = mock(StepExecution.class);

        // When
        tramoExportListener.beforeStep(stepExecution);

        // Then
        verify(tramoCSVWriter).writeNext(argThat(array -> {
            String[] header = (String[]) array;
            assertEquals("BARRIO", header[0]);
            assertEquals("COD_DISTRITO", header[1]);
            assertEquals("CODIGO_CALLE", header[2]);
            assertEquals("NOM_DISTRITO", header[3]);
            assertEquals("NOMBRE_CALLE", header[4]);
            assertEquals("PRIMER_NUM_TRAMO", header[5]);
            assertEquals("TIPO_VIA", header[6]);
            assertEquals("ULTIMO_NUM_TRAMO", header[7]);
            return true;
        }), eq(false));
    }

    @Test
    void afterStep_ClosesCSVWriter() throws IOException {
        // Given
        StepExecution stepExecution = mock(StepExecution.class);

        // When
        ExitStatus exitStatus = tramoExportListener.afterStep(stepExecution);

        // Then
        verify(tramoCSVWriter).close();
        assertEquals(ExitStatus.COMPLETED, exitStatus);
    }

    @Test
    void afterStep_ThrowsRuntimeException_OnIOException() throws IOException {
        // Given
        StepExecution stepExecution = mock(StepExecution.class);
        doThrow(new IOException("Error closing CSV writer")).when(tramoCSVWriter).close();

        // When
        try {
            tramoExportListener.afterStep(stepExecution);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertEquals("java.io.IOException: Error closing CSV writer", e.getMessage());
        }
    }
}

