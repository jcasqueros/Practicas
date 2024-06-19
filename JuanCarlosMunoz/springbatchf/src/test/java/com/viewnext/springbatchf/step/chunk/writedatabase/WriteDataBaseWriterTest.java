package com.viewnext.springbatchf.step.chunk.writedatabase;

import com.viewnext.springbatchf.model.entity.City;
import com.viewnext.springbatchf.model.entity.Order;
import com.viewnext.springbatchf.model.entity.User;
import com.viewnext.springbatchf.model.repository.jpa.CityRepository;
import com.viewnext.springbatchf.model.repository.jpa.OrderRepository;
import com.viewnext.springbatchf.model.repository.jpa.UserRepository;
import com.viewnext.springbatchf.util.ExtractObjectOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class WriteDataBaseWriterTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExtractObjectOperation extractObject;

    @InjectMocks
    private WriteDataBaseWriter writeDataBaseWriter;

    private User user;
    private City city;
    private Order order;

    @BeforeEach
     void init() {
        user = User.builder().name("John").dni("@Nif").address("street 13").build();
        city = City.builder().name("Caceres").codPostal(10002).build();
        order = Order.builder().price(93.2).numOrder(999999L).build();
    }

    @Test
     void testWrite_ValidList() throws Exception {


        List<Object> list = Arrays.asList(new Object[] { user, city, order });

        when(extractObject.extractObject(list)).thenReturn(new Object[] { user, city, order });

        writeDataBaseWriter.write(list);

        verify(userRepository).save(user);
        verify(cityRepository).save(city);
        verify(orderRepository).save(order);
    }

    @Test
     void testWrite_EmptyList() {
        List<Object> list = new ArrayList<>();

        Exception exception = assertThrows(Exception.class, () -> writeDataBaseWriter.write(list));
        assertEquals("List cannot be empty", exception.getMessage());
    }

    @Test
     void testWrite_InvalidObjectType() {
        List<Object> list = Arrays.asList(new Object[] { "Invalid object" });

        Exception exception = assertThrows(Exception.class, () -> writeDataBaseWriter.write(list));
        assertEquals("Invalid object type", exception.getMessage());
    }

    @Test
    void testExtractObjectsList_ValidList() throws Exception {
        List<Object> list = Arrays.asList(new Object[] { new Object[] { user, city, order } });

        writeDataBaseWriter.extractObjectsList(list);

        verify(userRepository).save(user);
        verify(cityRepository).save(city);
        verify(orderRepository).save(order);
    }




    @Test
    void testWriteFile_InvalidArray() {
        Object[] array = new Object[] { user, city, null};

        Exception exception = assertThrows(Exception.class, () -> writeDataBaseWriter.writeFile(array));
        assertEquals("Error to process list", exception.getMessage());
    }


}
