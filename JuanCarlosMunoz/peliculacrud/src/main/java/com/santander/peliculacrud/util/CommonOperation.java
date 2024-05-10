package com.santander.peliculacrud.util;

import jakarta.validation.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Set;

/**
 * The type Common operation.
 */
@Component
public class CommonOperation {

    @Autowired
    private Validator validator;

    /**
     * Show error model.
     *
     * @param logger
     *         the logger
     * @param bindingResult
     *         the binding result
     */
    public void showErrorModel(Logger logger, BindingResult bindingResult) {
        for (ObjectError objectError : bindingResult.getAllErrors()) {
            logger.error(objectError.getDefaultMessage());
        }
    }

}
