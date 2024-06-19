package com.viewnext.springbatchf.step.chunk.importdb;

import com.viewnext.springbatchf.model.entity.Street;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
 class ImportStreetProcessorTest {


    private ImportStreetProcessor importStreetProcessor;



    @Test
     void testProcess() {
        importStreetProcessor = new ImportStreetProcessor();
        importStreetProcessor.setFilter("filterValue");

        Street street = new Street();
        street.setNomDistrito("filterValue");

        Street result = importStreetProcessor.process(street);

        assertEquals(street, result);
    }

    @Test
     void testProcessNull() {
        importStreetProcessor = new ImportStreetProcessor();
        importStreetProcessor.setFilter("filterValue");

        Street street = new Street();
        street.setNomDistrito("otherValue");

        Street result = importStreetProcessor.process(street);

        assertNull(result);
    }
}

