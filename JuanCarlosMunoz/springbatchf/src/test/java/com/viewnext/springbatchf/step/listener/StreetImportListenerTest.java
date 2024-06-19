package com.viewnext.springbatchf.step.listener;

import com.viewnext.springbatchf.model.entity.District;
import com.viewnext.springbatchf.model.repository.jpa.DistrictRepository;
import com.viewnext.springbatchf.model.repository.jpa.StreetRepository;
import com.viewnext.springbatchf.step.chunk.importdb.ImportStreetProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StreetImportListenerTest {

    @Mock
    private StreetRepository streetRepository;

    @Mock
    private ImportStreetProcessor importStreetProcessor;

    @Mock
    private DistrictRepository districtRepository;

    @Mock
    private FileWriter logWriter;

    @Mock
    private StepExecution stepExecution;

    @InjectMocks
    private StreetImportListener streetImportListener;

    @Test
    void beforeStep_DeletesAllFromMongoDB() {
        // When
        streetImportListener.beforeStep(stepExecution);

        // Then
        verify(streetRepository).deleteAll();
        verify(districtRepository).deleteAll();
    }

    @Test
    void afterStep_AddsDistrictsToDB() {
        // Given
        HashMap<String, Integer> districts = new HashMap<>();
        districts.put("District 1", 10);
        districts.put("District 2", 20);
        when(importStreetProcessor.getDistrictCont()).thenReturn(districts);

        // When
        ExitStatus exitStatus = streetImportListener.afterStep(stepExecution);

        // Then
        verify(districtRepository, times(2)).save(any(District.class));
        assertEquals(ExitStatus.COMPLETED, exitStatus);
    }

    @Test
    void afterStep_ClosesLogWriter() throws IOException {
        // When
        ExitStatus exitStatus = streetImportListener.afterStep(stepExecution);

        // Then
        verify(logWriter).close();
        assertEquals(ExitStatus.COMPLETED, exitStatus);
    }

    @Test
    void afterStep_ThrowsRuntimeException_OnIOException() throws IOException {
        // Given
        doThrow(new IOException()).when(logWriter).close();

        // When and Then
        assertThrows(RuntimeException.class, () -> streetImportListener.afterStep(stepExecution));
    }
}
