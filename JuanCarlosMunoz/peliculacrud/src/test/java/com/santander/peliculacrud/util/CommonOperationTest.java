package com.santander.peliculacrud.util;


import com.santander.peliculacrud.model.bo.ActorBO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommonOperationTest {

    @Mock
    private Logger logger;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private CommonOperation commonOperation;



    @Test
    @DisplayName("Test to check show error model")
    void testShowErrorModel() {
        // Configuración
        List<ObjectError> errors = new ArrayList<>();
        ObjectError error1 = new ObjectError("error1", "Error 1");
        ObjectError error2 = new ObjectError("error2", "Error 2");
        errors.add(error1);
        errors.add(error2);

        when(bindingResult.getAllErrors()).thenReturn(errors);

        // Ejecución
        commonOperation.showErrorModel(logger, bindingResult);

        // Verificación
        verify(logger).error("Error 1");
        verify(logger).error("Error 2");
    }

    @Test
    void testGetIdObject() {
        // Configuración de datos de prueba
        List<ActorBO> actors = Arrays.asList(
                ActorBO.builder().id(1L).name("Actor 1").build(),
                ActorBO.builder().id(2L).name("Actor 2").build(),
                ActorBO.builder().id(3L).name("Actor 3").build()
        );

        // Ejecución del método
        List<Long> idObject = commonOperation.getIdObject(actors);

        // Verificaciones
        assertEquals(3, idObject.size());
        assertEquals(1L, idObject.get(0));
        assertEquals(2L, idObject.get(1));
        assertEquals(3L, idObject.get(2));
    }


}
