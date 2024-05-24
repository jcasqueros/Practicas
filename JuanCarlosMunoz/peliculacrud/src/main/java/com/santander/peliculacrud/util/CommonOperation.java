package com.santander.peliculacrud.util;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.bo.DirectorBO;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;


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


    public List<Long> getIdObject(List<Object> objs) {

        List<Long> idObject = new ArrayList<>();
        for (Object object : objs) {
            if (object instanceof ActorBO actor) {
                idObject.add(actor.getId());
            } else if (object instanceof DirectorBO director) {
                idObject.add(director.getId());
            }
        }
        return idObject;
    }


}
