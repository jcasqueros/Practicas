package com.santander.kafkaprovider.utils;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * The type Common operation.
 */
@Component
public class CommonOperation {

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
