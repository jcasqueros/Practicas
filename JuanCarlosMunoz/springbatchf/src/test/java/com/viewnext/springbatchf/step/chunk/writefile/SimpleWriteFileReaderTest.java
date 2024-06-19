package com.viewnext.springbatchf.step.chunk.writefile;


import com.opencsv.CSVReader;
import com.viewnext.springbatchf.model.entity.City;
import com.viewnext.springbatchf.model.entity.Order;
import com.viewnext.springbatchf.model.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimpleWriteFileReaderTest {

    @Mock
    private Resource resource;

    @Mock
    private CSVReader csvReader;

    @InjectMocks
    private SimpleWriteFileReader simpleWriteFileReader;

    @Test
    void read_ReadsNextRowFromCSV() throws Exception {
        String[] row = new String[] { "user1", "dni1", "address1", "city1", "12345", "10.99", "1" };
        when(csvReader.readNext()).thenReturn(row);

        Object[] objects = simpleWriteFileReader.read();

        assertNotNull(objects);
        assertEquals(3, objects.length);
        assertEquals(User.builder().name("user1").dni("dni1").address("address1").build(), objects[0]);
        assertEquals(City.builder().name("city1").codPostal(12345).build(), objects[1]);
        assertEquals(Order.builder().price(10.99).numOrder(1L).build(), objects[2]);
    }

    @Test
    void read_ReturnsNullWhenEOF() throws Exception {
        when(csvReader.readNext()).thenReturn(null);

        Object[] objects = simpleWriteFileReader.read();

        assertNull(objects);
    }

    @Test
    void read_ThrowsItemStreamException_WhenCSVFileHasLessThan7Columns() throws Exception {
        String[] row = new String[] { "user1", "dni1", "address1", "city1" };
        when(csvReader.readNext()).thenReturn(row);

        assertThrows(ItemStreamException.class, () -> simpleWriteFileReader.read());
    }

    @Test
    void close_ClosesCSVReader() throws IOException {
        simpleWriteFileReader.close();

        verify(csvReader).close();
    }
}
