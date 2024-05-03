package com.viewnext.bsan.practica03.dao;

import com.viewnext.bsan.practica03.entity.User;
import com.viewnext.bsan.practica03.exception.dao.BadUpsertException;
import com.viewnext.bsan.practica03.exception.dao.DaoLevelException;

import java.util.List;
import java.util.Optional;

/**
 * The {@code UserDao} interface defines data-access logic while also applying an anticorruption layer pattern.
 *
 * @author Antonio Gil
 */
public interface UserDao {

    /**
     * Retrieves all the users registered in the system.
     *
     * @return A list containing all the users registered in the system
     * @throws DaoLevelException if the operation somehow fails
     */
    List<User> getAll() throws DaoLevelException;

    /**
     * Retrieves a user by its DNI, if it exists.
     *
     * @param dni The DNI for the user
     * @return An {@code Optional} containing the found user, if it exists; otherwise, an empty {@code Optional}
     * @throws DaoLevelException if the operation somehow fails
     */
    Optional<User> getByDni(String dni) throws DaoLevelException;

    /**
     * Creates a new user with the given information.
     *
     * @param user The user that shall be created
     * @return The user as saved to the underlying persistence engine
     * @throws BadUpsertException if a constraint violation happens
     * @throws DaoLevelException  if the operation fails for any other reasons
     */
    User create(User user) throws DaoLevelException;

    /**
     * Given a user's DNI, updates all their information.
     *
     * @param user The new information for the user
     * @return The user as saved to the underlying persistence engine
     * @throws BadUpsertException if a constraint violation happens
     * @throws DaoLevelException  if the operation fails for any other reasons
     */
    User update(User user) throws DaoLevelException;

    /**
     * Deletes a user given their DNI.
     *
     * @param dni The DNI for the user
     * @throws DaoLevelException if the operation somehow fails
     */
    void deleteByDni(String dni) throws DaoLevelException;

}
