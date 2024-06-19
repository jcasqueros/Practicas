package com.viewnext.springbatchf.step.skippolicy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.item.file.FlatFileParseException;

import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StreetSkipPolicyTest {

    @Mock
    private FileWriter logWriter;

    @Mock
    private Logger logger;

    @InjectMocks
    private StreetSkipPolicy streetSkipPolicy;


    @Test
    void shouldNotSkip_WhenNotFlatFileParseException() throws SkipLimitExceededException {
        // Given
        Throwable throwable = new RuntimeException();

        // When
        boolean skip = streetSkipPolicy.shouldSkip(throwable, 1);

        // Then
        assertFalse(skip);
    }

    @Test
    void shouldThrowRuntimeException_WhenIOException() throws IOException, SkipLimitExceededException {
        // Given
        FlatFileParseException ffpe = mock(FlatFileParseException.class);
        when(ffpe.getLineNumber()).thenReturn(1);
        when(ffpe.getInput()).thenReturn("input");
        doThrow(new IOException()).when(logWriter).write(anyString());

        // When and Then
        assertThrows(RuntimeException.class, () -> streetSkipPolicy.shouldSkip(ffpe, 1));
    }
}

