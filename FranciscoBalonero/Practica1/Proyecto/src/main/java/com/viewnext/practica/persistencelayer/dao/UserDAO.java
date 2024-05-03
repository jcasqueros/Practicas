package com.viewnext.practica.persistencelayer.dao;

import com.viewnext.practica.persistencelayer.entity.User;
import com.viewnext.practica.persistencelayer.exception.PersistenceLayerException;

import java.util.List;

/**
 * Interface containing the UserDAO methods. This interface provides methods for interacting with User data in the
 * persistence layer. Implementations of this interface handle the storage, retrieval, and deletion of user entities.
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * try {
 *     // Data access operation to save a user
 *     User savedUser = userDAO.save(newUser);
 *
 *     // Data access operation to find a user by DNI
 *     User foundUser = userDAO.findUserByDni("12345678A");
 *
 *     // Data access operation to retrieve a list of all users
 *     List<User> allUsers = userDAO.findAllUsers();
 *
 *     // Data access operation to delete a user by DNI
 *     userDAO.deleteUserByDni("12345678A");
 *
 *      // Data access operation to save a user
 *      User updatedUser = userDAO.updateUser(updated);
 * }
 * </pre>
 *
 * @author Francisco Balonero Olivera
 */
public interface UserDAO {

    /**
     * Save a User
     *
     * @param user
     *         {@link User} user to save
     * @return Saved {@link User}
     * @throws PersistenceLayerException
     *         If there is an issue saving the user.
     */
    User save(User user) throws PersistenceLayerException;

    /**
     * Retrieves a list of all users.
     *
     * @return {@link List} A list with all the users.
     * @throws PersistenceLayerException
     *         If there is an issue retrieving the users.
     */
    List<User> findAllUsers() throws PersistenceLayerException;

    /**
     * Finds a user by its ID.
     *
     * @param dni
     *         {@link String} The DNI of the user to find.
     * @return {@link User} The user found.
     * @throws PersistenceLayerException
     *         If there is an issue retrieving the user.
     */
    User findUserByDni(String dni) throws PersistenceLayerException;

    /**
     * Deletes a user by its DNI.
     *
     * @param dni
     *         {@link String} The DNI of the user to be deleted.
     * @throws PersistenceLayerException
     *         If there is an issue deleting the user.
     */
    void deleteUserByDni(String dni) throws PersistenceLayerException;

    /**
     * Update a User
     *
     * @param user
     *         {@link User} user to update
     * @return Updated {@link User}
     * @throws PersistenceLayerException
     *         If there is an issue updating the user.
     */
    User updateUser(User user) throws PersistenceLayerException;
}
