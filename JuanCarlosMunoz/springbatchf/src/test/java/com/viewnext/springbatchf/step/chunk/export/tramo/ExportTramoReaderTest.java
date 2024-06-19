package com.viewnext.springbatchf.step.chunk.export.tramo;

import com.viewnext.springbatchf.model.entity.Street;
import com.viewnext.springbatchf.model.repository.jpa.StreetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class ExportTramoReaderTest {

    @Mock
    private StreetRepository streetRepository;

    @InjectMocks
    private ExportTramoReader exportTramoReader;

    @Test
     void testOpen() throws ItemStreamException {
        List<Street> streets = Arrays.asList(new Street(), new Street());
        when(streetRepository.findAll()).thenReturn(streets);

        exportTramoReader.open(new ExecutionContext());

        verify(streetRepository).findAll();
    }

    @Test
     void testRead() {
        List<Street> streets = Arrays.asList(new Street(), new Street());
        when(streetRepository.findAll()).thenReturn(streets);

        exportTramoReader.open(new ExecutionContext());

        Street street = exportTramoReader.read();
        assertEquals(streets.get(0), street);

        street = exportTramoReader.read();
        assertEquals(streets.get(1), street);

        street = exportTramoReader.read();
        assertNull(street);
    }

    @Test
    void testUpdate() throws ItemStreamException {
        exportTramoReader.update(new ExecutionContext());
        verifyNoInteractions(streetRepository);
    }

    @Test
    void testClose() throws ItemStreamException {
        exportTramoReader.close();
        verifyNoInteractions(streetRepository);
    }
}
