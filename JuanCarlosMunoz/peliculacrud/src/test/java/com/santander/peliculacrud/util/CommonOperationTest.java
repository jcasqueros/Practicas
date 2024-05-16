package com.santander.peliculacrud.util;


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
import java.util.List;

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

}
