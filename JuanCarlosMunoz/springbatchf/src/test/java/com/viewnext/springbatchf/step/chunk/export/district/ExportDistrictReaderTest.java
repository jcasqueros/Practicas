package com.viewnext.springbatchf.step.chunk.export.district;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.viewnext.springbatchf.model.entity.District;
import com.viewnext.springbatchf.model.repository.jpa.DistrictRepository;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class ExportDistrictReaderTest {

    @Mock
    private DistrictRepository districtRepository;

    @InjectMocks
    private ExportDistrictReader exportDistrictReader;

    @Test
     void testOpen() throws ItemStreamException {
        List<District> districts = Arrays.asList(new District(), new District());

        when(districtRepository.findAll()).thenReturn(districts);

        exportDistrictReader.open(new ExecutionContext());

        verify(districtRepository).findAll();
    }

    @Test
     void testRead() {
        List<District> districts = Arrays.asList(new District(), new District());

        when(districtRepository.findAll()).thenReturn(districts);

        exportDistrictReader.open(new ExecutionContext());

        District district = exportDistrictReader.read();
        assertEquals(districts.get(0), district);

        district = exportDistrictReader.read();
        assertEquals(districts.get(1), district);

        district = exportDistrictReader.read();
        assertNull(district);
    }

    @Test
    void testUpdate() throws ItemStreamException {
        exportDistrictReader.update(new ExecutionContext());
        verifyNoInteractions(districtRepository);
    }

    @Test
    void testClose() throws ItemStreamException {
        exportDistrictReader.close();
        verifyNoInteractions(districtRepository);
    }

}
