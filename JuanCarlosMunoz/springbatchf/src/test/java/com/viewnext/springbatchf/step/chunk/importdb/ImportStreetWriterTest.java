package com.viewnext.springbatchf.step.chunk.importdb;

import com.viewnext.springbatchf.model.entity.Street;
import com.viewnext.springbatchf.model.repository.jpa.StreetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
 class ImportStreetWriterTest {

    @Mock
    private StreetRepository streetRepository;

    @InjectMocks
    private ImportStreetWriter importStreetWriter;

    @Test
     void testWrite() {
        List<Street> streets = Arrays.asList(new Street(), new Street());

        importStreetWriter.write(streets);

        verify(streetRepository, times(2)).save(any(Street.class));
    }
}

