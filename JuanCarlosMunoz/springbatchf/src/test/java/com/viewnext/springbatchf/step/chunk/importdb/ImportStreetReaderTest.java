package com.viewnext.springbatchf.step.chunk.importdb;

import com.viewnext.springbatchf.model.entity.Street;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
 class ImportStreetReaderTest {

    @Test
     void testReader() {
        ImportStreetReader importStreetReader = new ImportStreetReader();
        FlatFileItemReader<Street> reader = importStreetReader.reader(new ClassPathResource("streets.csv"));

        assertNotNull(reader);
    }
}
