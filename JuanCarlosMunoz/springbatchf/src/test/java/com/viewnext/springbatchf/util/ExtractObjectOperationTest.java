package com.viewnext.springbatchf.util;

import com.viewnext.springbatchf.model.entity.City;
import com.viewnext.springbatchf.model.entity.Order;
import com.viewnext.springbatchf.model.entity.User;
import com.viewnext.springbatchf.util.exception.GenericException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ExtractObjectOperationTest {

    @InjectMocks
    private ExtractObjectOperation extractObjectOperation;

    @Test
    void testExtractObjectValdniData() throws Exception {
        User user = User.builder()
                .dni("1L")
                .name("Nombre")
                .build();
        City city = City.builder()
                .codPostal(10000)
                .name("Ciudad")
                .build();
        Order order = Order.builder()
                .numOrder(1L)
                .price(12.22)
                .build();

        List<Object> list = new ArrayList<>();
        list.add(user);
        list.add(city);
        list.add(order);

        Object[] array = extractObjectOperation.extractObject(list);

        assertArrayEquals(new Object[]{user, city, order}, array);
    }

    @Test
    void testExtractObjectInvaldniData() {
        List<Object> list = new ArrayList<>();
        list.add("Invalid object type");

        GenericException exception = assertThrows(GenericException.class, () -> extractObjectOperation.extractObject(list));
        Assertions.assertEquals("Invalid object type", exception.getMessage());
    }

    @Test
    void testExtractObjectNullList() {
        List<Object> list = null;

        NullPointerException exception = assertThrows(NullPointerException.class, () -> extractObjectOperation.extractObject(list));
        Assertions.assertEquals("list cannot be null", exception.getMessage());
    }

    @Test
    void testExtractObjectEmptyList() throws Exception {
        List<Object> list = new ArrayList<>();

        Object[] array = extractObjectOperation.extractObject(list);

        assertArrayEquals(new Object[3], array);
    }
}

