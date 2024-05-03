package com.viewnext.practica.businesslayer.service;

import com.viewnext.practica.businesslayer.bo.UserBO;
import com.viewnext.practica.businesslayer.exception.BusinessException;
import com.viewnext.practica.persistencelayer.entity.User;

import java.util.List;

/**
 * Service interface containing methods for managing users.
 *
 * <p>This interface declares methods for retrieving, registering, updating, and deleting users.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * // Obtain an instance of UserService
 * UserService userService = // instantiate or inject the implementation
 *
 * // Retrieve all users
 * List<UserBO> allUsers = userService.getAll();
 *
 * // Register a new user
 * UserBO newUser = // create a new user instance
 * UserBO registeredUser = userService.save(newUser);
 *
 * // Retrieve a user by DNI
 * UserBO retrievedUser = userService.getUserByDni("12345678A");
 *
 * // Delete a user by DNI
 * userService.deleteUser("12345678A");
 *
 * // Update a user
 * UserBO registeredUser = userService.save(updateUser);
 * }
 * </pre>
 *
 * <p>Implementing classes should handle {@link BusinessException} to appropriately manage exceptions
 * related to business operations on users.</p>
 *
 * <p>Instances of this interface are typically used in the business layer of the application to provide
 * a clean separation between the business logic and persistence layer logic.</p>
 *
 * @author Francisco Balonero Olivera
 */
public interface UserService {

    /**
     * save a new user.
     *
     * @param userBO
     *         {@link UserBO} The user BO to save
     * @return {@link UserBO} The registered user BO
     * @throws BusinessException
     *         If an error occurs during the operation
     */
    UserBO save(UserBO userBO) throws BusinessException;

    /**
     * Retrieves all users.
     *
     * @return {@link List} A list of all users
     * @throws BusinessException
     *         If an error occurs during the operation
     */
    List<UserBO> getAll() throws BusinessException;

    /**
     * Finds a user by its ID.
     *
     * @param dni
     *         {@link String} The DNI of the user to find.
     * @return {@link User} The user found.
     * @throws BusinessException
     *         If there is an issue retrieving the user.
     */
    UserBO getUserByDni(String dni) throws BusinessException;

    /**
     * Deletes a user by its DNI.
     *
     * @param dni
     *         {@link String} The DNI of the user to be deleted.
     * @throws BusinessException
     *         If there is an issue deleting the user.
     */
    void deleteUser(String dni) throws BusinessException;

    /**
     * Update a User
     *
     * @param userBO
     *         {@link UserBO} user to update
     * @return Updated {@link User}
     * @throws BusinessException
     *         If there is an issue updating the user.
     */
    UserBO updateUser(UserBO userBO) throws BusinessException;

}
