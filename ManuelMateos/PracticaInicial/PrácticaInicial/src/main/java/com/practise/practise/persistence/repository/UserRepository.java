package com.practise.practise.persistence.repository;

import com.practise.practise.persistence.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository of the class {@link User}
 *
 * @author Manuel Mateos de Torres
 * @see {@link JpaRepository}
 */
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Get all users
     *
     * @return User List
     */
    @Query(nativeQuery = true, value = "SELECT * FROM users")
    List<User> findAllUsers();

    /**
     * Get user by his id
     *
     * @param dni
     * @return Optional User
     */
    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE dni = :dni")
    Optional<User> findUserById(@Param("dni") String dni);

    /**
     * Create a user
     *
     * @param dni
     * @param name
     * @param surname
     * @param age
     */
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO users (dni, name, surname, age) VALUES (:dni, :name, :surname, :age)")
    void saveUser(@Param("dni") String dni, @Param("name") String name, @Param("surname") String surname,
            @Param("age") int age);

    /**
     * Update user
     *
     * @param dni
     * @param name
     * @param surname
     * @param age
     */
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE users SET name = :name, surname = :surname, age = :age WHERE dni = :dni")
    void updateUser(@Param("dni") String dni, @Param("name") String name, @Param("surname") String surname,
            @Param("age") int age);

    /**
     * Delete user by his id
     *
     * @param dni
     */
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM users WHERE dni = :dni")
    void deleteUserById(@Param("dni") String dni);
}
