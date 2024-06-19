package com.viewnext.springbatchf.step.chunk.export.district;

import com.viewnext.springbatchf.model.entity.District;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)

class ExportDistrictProcessorTest {
    @Test
    void testProcess() {
        District district = new District();

        ExportDistrictProcessor processor = new ExportDistrictProcessor();

        District result = processor.process(district);

        assertEquals(district, result);
    }

}
