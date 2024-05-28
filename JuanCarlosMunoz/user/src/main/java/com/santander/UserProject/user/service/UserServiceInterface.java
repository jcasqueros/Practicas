package com.santander.UserProject.user.service;

import com.santander.UserProject.user.model.bo.UserBO;
import com.santander.UserProject.user.util.exception.GenericException;

import java.util.List;

/**
 * The interface Users service interface.
 */
public interface UserServiceInterface {
    /**
     * Create user.
     *
     * @param userBO
     *         the user bo
     * @return the user bo
     * @throws GenericException
     *         the generic exception
     */
    UserBO createUser(UserBO userBO) throws GenericException;

    /**
     * Gets user by id.
     *
     * @param id
     *         the id
     * @return the user by id
     */
    UserBO getUserById(long id);

    /**
     * Update user boolean.
     *
     * @param id
     *         the id
     * @param userBO
     *         the user bo
     * @return the boolean
     * @throws GenericException
     *         the generic exception
     */
    boolean updateUser(long id, UserBO userBO) throws GenericException;

    /**
     * Delete user boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     * @throws GenericException
     *         the generic exception
     */
    boolean deleteUser(long id) throws GenericException;

    /**
     * Gets user by name.
     *
     * @param name
     *         the name
     * @param age
     *         the age
     * @param page
     *         the page
     * @return the user by name
     */
    List<UserBO> getUserByNameAge(String name,int age, int page);


}
