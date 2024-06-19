package com.viewnext.springbatchf.step.chunk.export.district;

import com.opencsv.CSVWriter;
import com.viewnext.springbatchf.model.entity.District;
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
class ExportDistrictWriterTest {

    @Mock
    private CSVWriter districtCSVWriter;

    @InjectMocks
    private ExportDistrictWriter exportDistrictWriter;

    @Test
    void testWrite() {
        List<District> districts = Arrays.asList(new District(), new District());

        exportDistrictWriter.write(districts);

        verify(districtCSVWriter, times(2)).writeNext(any(String[].class), eq(false));
    }

}
