package com.viewnext.springbatchf.util.exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class GenericExceptionTest {

    @Test
    public void testGenericException_withMessage() {
        String message = "This is a test message";
        GenericException exception = new GenericException(message);
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testGenericException_withNullMessage() {
        GenericException exception = new GenericException(null);
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    public void testGenericException_withEmptyMessage() {
        GenericException exception = new GenericException("");
        assertNotNull(exception);
        assertEquals("", exception.getMessage());
    }
}

