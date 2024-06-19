package com.viewnext.springbatchf.step.chunk.export.tramo;

import com.viewnext.springbatchf.model.entity.Street;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ExportTramoProcessorTest {

    @Test
    void testProcess() {
        ExportTramoProcessor processor = new ExportTramoProcessor();
        Street street = new Street();

        Street result = processor.process(street);

        assertEquals(street, result);
    }
}
