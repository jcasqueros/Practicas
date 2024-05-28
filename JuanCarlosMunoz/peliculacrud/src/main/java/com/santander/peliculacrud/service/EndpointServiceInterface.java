package com.santander.peliculacrud.service;

import com.santander.peliculacrud.model.dto.UserDTO;

import java.util.List;

/**
 * The interface Endpoint service interface.
 */
public interface EndpointServiceInterface {

    /**
     * Gets user byname.
     *
     * @param name
     *         the name
     * @param age
     *         the age
     * @return the user byname
     */
    List<UserDTO> getUserByNameAndAge(String name, int age);
}
