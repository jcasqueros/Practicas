package com.practise.practise.persistence.dao;

import com.practise.practise.exceptions.DataAccessException;
import com.practise.practise.persistence.models.User;

import java.util.List;

/**
 * DAO of the model {@link User}
 *
 * @author Manuel Mateos de Torres
 */
public interface UserDAO {

    /**
     * @throws DataAccessException
     * @returnList of user
     */
    List<User> findAll() throws DataAccessException;

    /**
     * Get a user by his id
     *
     * @param dni
     * @return
     * @throws DataAccessException
     */
    User findById(String dni) throws DataAccessException;

    /**
     * Save a user in the database
     *
     * @param user
     * @return
     * @throws DataAccessException
     */
    User save(User user) throws DataAccessException;

    /**
     * Update an existing user
     *
     * @param user
     * @return
     * @throws DataAccessException
     */
    User update(User user) throws DataAccessException;

    /**
     * Delete a user of the database
     *
     * @param dni
     * @throws DataAccessException
     */
    void deleteById(String dni) throws DataAccessException;
}
