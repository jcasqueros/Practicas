package com.viewnext.springbatchf.util;

import com.viewnext.springbatchf.model.entity.City;
import com.viewnext.springbatchf.model.entity.Order;
import com.viewnext.springbatchf.model.entity.User;
import com.viewnext.springbatchf.util.exception.GenericException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Extract object operation.
 */
@Component
public class ExtractObjectOperation {

    /**
     * Extract object object [ ].
     *
     * @param list
     *         the list
     * @return the object [ ]
     * @throws Exception
     *         the exception
     */
    public Object[] extractObject(List<?> list) throws Exception {

        if (list == null ){
            throw  new NullPointerException("list cannot be null");
        }
        Object[] array = new Object[3];

        for (Object object : list) {
            if (object instanceof User) {
                User user = (User) object;
                array[0] = user;
            } else if (object instanceof City) {
                City city = (City) object;
                array[1] = city;

            } else if (object instanceof Order) {
                Order order = (Order) object;
                array[2] = order;
            } else {
                throw new GenericException("Invalid object type");
            }
        }
        return array;
    }
}
