package com.viewnext.springbatchf.step.chunk.writefile;

import com.viewnext.springbatchf.model.entity.City;
import com.viewnext.springbatchf.model.entity.Order;
import com.viewnext.springbatchf.model.entity.User;
import com.viewnext.springbatchf.util.ExtractObjectOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WriteFileWriterTest {

    @Mock
    private ExtractObjectOperation extractObject;

    @Value("${log.resource.finalFileUser}")
    private String finalFilePath;

    @InjectMocks
    private WriteFileWriter writeFileWriter;

    private User user;
    private City city;
    private Order order;

    @BeforeEach
    void init() {

        user = User.builder().name("John").dni("12345678A").address("address1").build();
        city = City.builder().codPostal(10002).name("Caceres").build();
        order = Order.builder().price(10.9).numOrder(1L).build();
    }

    @Test
    void write_ThrowsException_WhenListIsEmpty() {
        List<Object> list = new ArrayList<>();

        assertThrows(Exception.class, () -> writeFileWriter.write(list));
    }

    @Test
    void write_WritesToFile_WhenListHasThreeElements() throws Exception {
        // Given
        List<Object> list = new ArrayList<>();
        list.add(user);
        list.add(city);
        list.add(order);

        Object[] objects = new Object[3];
        objects[0] = user;
        objects[1] = city;
        objects[2] = order;

        when(extractObject.extractObject(list)).thenReturn(objects);

        // When
        writeFileWriter.write(list);

        // Then
        verify(extractObject, times(1)).extractObject(list);

    }

    @Test
    void write_WritesToFile_WhenListHasArrayOfObjects() throws Exception {
        // Given
        List<Object> list = new ArrayList<>();
        list.add(user);
        list.add(city);
        list.add(order);

        Object[] objects = new Object[3];
        objects[0] = user;
        objects[1] = city;
        objects[2] = order;

        when(extractObject.extractObject(list)).thenReturn(objects);

        // When
        writeFileWriter.write(list);

        // Then
        verify(extractObject, times(1)).extractObject(list);
    }


    @Test
    void write_ThrowsException_WhenListHasLessThanThreeElements() {
        // Given
        List<Object> list = new ArrayList<>();
        list.add(user);

        // When and Then
        assertThrows(Exception.class, () -> writeFileWriter.write(list));
    }

}
