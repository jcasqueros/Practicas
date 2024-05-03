package com.viewnext.practica.persistencelayer.repository;

import com.viewnext.practica.persistencelayer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Interface for performing CRUD operations on users table.
 *
 * <p>This interface extends the JpaRepository interface, providing methods for performing CRUD (Create, Read, Update,
 * Delete) operations on User entities.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * // Query operation to find a user by their DNI
 * Optional<User> userByDni = userRepository.findUserByDni("12345678A");
 *
 * // Query operation to find all users
 * List<User> allUsers = userRepository.findAllUsers();
 *
 * // Query operation to delete a user by DNI
 * int result = userRepository.deleteUserByDni("12345678A");
 *
 * // Query operation to update user
 * int result = userRepository.updateUser("12345678A", "updated", "updated", 55);
 *
 * // Query operation to save a user
 * userRepository.saveUser("12345678A", "Jhon", "Doe", 55);
 * }
 * </pre>
 *
 * @author Francisco Balonero Olivera
 */
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Finds all users
     *
     * @return {@link List} with all {@link User}
     */
    @Query(nativeQuery = true, value = "SELECT * FROM users")
    List<User> findAllUsers();

    /**
     * Finds a user by their DNI
     *
     * @param dni
     *         {@link String} the DNI of the user
     * @return {@link Optional} the {@link User} with the dni or null
     */
    @Query(nativeQuery = true, value = "SELECT * FROM users u WHERE u.dni = :dni")
    Optional<User> findUserByDni(@Param("dni") String dni);

    /**
     * Delete a user by id
     *
     * @param dni
     *         {@link String} the DNI of the user
     * @return {@link Integer} the number of rows that have been affected by the query
     */
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM users u WHERE u.dni = :dni")
    int deleteUserByDni(@Param("dni") String dni);

    /**
     * Save a user
     *
     * @param dni
     *         {@link String} the DNI of the user
     * @param name
     *         {@link String} the name of the user
     * @param surname
     *         {@link String} the surname of the user
     * @param age
     *         {@link Integer} the age of the user
     */
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO users (dni, name, surname, age) VALUES (:dni , :name, :surname, :age)")
    void saveUser(@Param("dni") String dni, @Param("name") String name, @Param("surname") String surname,
            @Param("age") int age);

    /**
     * Update a user
     *
     * @param dni
     *         {@link String} the DNI of the user
     * @param name
     *         {@link String} the name of the user
     * @param surname
     *         {@link String} the surname of the user
     * @param age
     *         {@link Integer} the age of the user
     * @return {@link Integer} the number of rows that have been affected by the query
     */
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE users u SET u.name = :name, u.surname = :surname, u.age = :age WHERE u.dni = :dni")
    int updateUser(@Param("dni") String dni, @Param("name") String name, @Param("surname") String surname,
            @Param("age") int age);
}
