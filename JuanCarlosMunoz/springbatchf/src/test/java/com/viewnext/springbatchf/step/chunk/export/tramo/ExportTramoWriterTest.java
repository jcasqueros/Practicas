package com.viewnext.springbatchf.step.chunk.export.tramo;

import com.opencsv.CSVWriter;
import com.viewnext.springbatchf.model.entity.Street;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
 class ExportTramoWriterTest {

    @Mock
    private CSVWriter tramoCSVWriter;

    @InjectMocks
    private ExportTramoWriter exportTramoWriter;

    @Test
     void testWrite() {
        List<Street> streets = Arrays.asList(new Street(), new Street());

        exportTramoWriter.write(streets);

        verify(tramoCSVWriter, times(2)).writeNext(any(String[].class), eq(false));
    }
}

