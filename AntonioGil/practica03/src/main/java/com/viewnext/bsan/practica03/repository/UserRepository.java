package com.viewnext.bsan.practica03.repository;

import com.viewnext.bsan.practica03.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The {@code UserRepository} class defines a JPA repository for User objects.
 *
 * @author Antonio Gil
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Retrieves all the users registered in the system.
     *
     * @return A list containing all the users registered in the system
     */
    @Query(nativeQuery = true, value = "SELECT * FROM M_USER")
    List<User> nqFindAll();

    /**
     * Retrieves a user by its DNI, if it exists.
     *
     * @param dni The DNI for the user
     * @return An {@code Optional} containing the found user, if it exists; otherwise, an empty {@code Optional}
     */
    @Query(nativeQuery = true, value = "SELECT * FROM M_USER WHERE M_DNI = :dni")
    Optional<User> nqFindByDni(@Param("dni") String dni);

    /**
     * Creates a new user with the given information.
     *
     * @param dni     The DNI for the user
     * @param name    The name for the user
     * @param surname The surname for the user
     * @param age     The age for the user
     */
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO M_USER (M_DNI, M_NAME, M_SURNAME, M_AGE) VALUES (:dni, :name, :surname, :age)")
    void nqCreate(@Param("dni") String dni, @Param("name") String name, @Param("surname") String surname, @Param("age") int age);

    /**
     * Given a user's DNI, updates all their information, except for the DNI itself.
     *
     * @param dni        The DNI for the user
     * @param newName    The new newName for the user
     * @param newSurname The new surname for the user
     * @param newAge     The new age for the user
     */
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE M_USER SET M_NAME = :new_name, M_SURNAME = :new_surname, M_AGE = :new_age WHERE M_DNI = :dni")
    void nqUpdate(@Param("dni") String dni, @Param("new_name") String newName, @Param("new_surname") String newSurname,
                  @Param("new_age") int newAge);

    /**
     * Deletes a user given their DNI.
     *
     * @param dni The DNI for the user
     */
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM M_USER WHERE M_DNI = :dni")
    void nqDeleteByDni(@Param("dni") String dni);

}
