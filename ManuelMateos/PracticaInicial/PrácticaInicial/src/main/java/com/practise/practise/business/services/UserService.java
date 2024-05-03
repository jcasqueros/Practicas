package com.practise.practise.business.services;

import com.practise.practise.business.bo.UserBO;
import com.practise.practise.exceptions.ServiceException;
import com.practise.practise.exceptions.UpsertException;
import com.practise.practise.persistence.models.User;

import java.util.List;

/**
 * Service of the model {@link User}
 *
 * @author Manuel Mateos de Torres
 */
public interface UserService {

    /**
     * Method for list all users
     *
     * @return User list
     * @throws ServiceException
     */
    List<UserBO> findAll() throws ServiceException;

    /**
     * Method for get a user by his id
     *
     * @param dni
     * @return User
     * @throws ServiceException
     */
    UserBO findById(String dni) throws ServiceException;

    /**
     * Method for create a user
     *
     * @param userBO
     * @return UserBO
     * @throws ServiceException
     */
    UserBO save(UserBO userBO) throws ServiceException;

    /**
     * Method for update a user
     *
     * @param userBO
     * @return UserBO
     * @throws ServiceException
     */
    UserBO update(UserBO userBO) throws ServiceException, UpsertException;

    /**
     * Method for delete a user
     *
     * @param dni
     */
    void delete(String dni) throws ServiceException;
}
