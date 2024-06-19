package com.viewnext.springbatchf.step.chunk.writefile;

import com.viewnext.springbatchf.util.exception.GenericException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
 class WriteFileProcessorTest {

    @Test
     void testProcess_ValidObject() throws NullPointerException {
        WriteFileProcessor processor = new WriteFileProcessor();
        Object object = new Object();
        Object result = processor.process(object);
        assertEquals(object, result);
    }

    @Test
     void testProcess_NullObject() {
        WriteFileProcessor processor = new WriteFileProcessor();
        Exception exception = assertThrows(NullPointerException.class, () -> processor.process(null));
        assertEquals("Object cannot be null", exception.getMessage());
    }
}

