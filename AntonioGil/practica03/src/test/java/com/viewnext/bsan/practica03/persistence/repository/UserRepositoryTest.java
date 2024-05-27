package com.viewnext.bsan.practica03.persistence.repository;

import com.viewnext.bsan.practica03.persistence.entity.User;
import com.viewnext.bsan.practica03.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@code UserRepository}.
 *
 * @author Antonio Gil
 */
@DataJpaTest
class UserRepositoryTest {

    final UserRepository repository;

    static final User SAMPLE_USER = User.builder().dni("11111111H").name("JOSE").surname("DOMINGUEZ").age(35).build();

    @Autowired
    public UserRepositoryTest(UserRepository repository) {
        this.repository = repository;
        repository.save(SAMPLE_USER);
    }

    @BeforeEach
    void setup() {
    }

    @DisplayName("[UserRepositoryTest] [UNIT] nqFindAll")
    @Test
    void givenUsers_whenNqFindAll_thenReturnUserList() {
        final long expectedSize = 1;

        final List<User> foundUsers = repository.nqFindAll();

        assertEquals(expectedSize, foundUsers.size());
        assertTrue(foundUsers.contains(SAMPLE_USER));
    }

    @DisplayName("[UserRepositoryTest] [UNIT] nqFindByDni (non-existing user)")
    @Test
    void givenNonExistentDni_whenNqFindByDni_thenReturnEmpty() {
        final String dni = "NON_EXISTENT_DNI";

        final Optional<User> foundUserAsOptional = repository.nqFindByDni(dni);

        assertTrue(foundUserAsOptional.isEmpty());
    }

    @DisplayName("[UserRepositoryTest] [UNIT] nqFindByDni (existing user)")
    @Test
    void givenUserDni_whenNqFindByDni_thenReturnUser() {
        final String dni = SAMPLE_USER.getDni();

        final User foundUser = repository.nqFindByDni(dni).orElseThrow();

        assertEquals(SAMPLE_USER.getDni(), foundUser.getDni());
        assertEquals(SAMPLE_USER.getName(), foundUser.getName());
        assertEquals(SAMPLE_USER.getSurname(), foundUser.getSurname());
        assertEquals(SAMPLE_USER.getAge(), foundUser.getAge());
    }

    @DisplayName("[UserRepositoryTest] [UNIT] nqCreate")
    @Test
    void givenUserData_whenNqCreate_thenUserIsCreated() {
        final User newUser = User.builder().dni("44444444A").name("JORGE").surname("LOZANO").age(25).build();
        final long userCountBeforeSave = repository.count();

        repository.nqCreate(newUser.getDni(), newUser.getName(), newUser.getSurname(), newUser.getAge());
        final long userCountAfterSave = repository.count();

        assertTrue(repository.existsById(newUser.getDni()));
        assertEquals(userCountBeforeSave + 1, userCountAfterSave);
    }

    @DisplayName("[UserRepositoryTest] [UNIT] nqUpdate")
    @Test
    void givenUserData_whenUpdate_thenUserIsUpdated() {
        final String dni = SAMPLE_USER.getDni();
        final String newName = "JUAN";
        final String newSurname = "Y MEDIO";
        final int newAge = 61;

        repository.nqUpdate(dni, newName, newSurname, newAge);

        final User foundUser = repository.findById(dni).orElseThrow();

        assertEquals(newName, foundUser.getName());
        assertEquals(newSurname, foundUser.getSurname());
        assertEquals(newAge, foundUser.getAge());
    }

    @DisplayName("[UserRepositoryTest] [UNIT] nqDeleteById")
    @Test
    void givenUserDni_whenDeleteByDni_thenUserIsRemoved() {
        final String dni = SAMPLE_USER.getDni();
        final long userCountBeforeDelete = repository.count();

        repository.nqDeleteByDni(dni);
        final long userCountAfterDelete = repository.count();

        assertEquals(userCountBeforeDelete - 1, userCountAfterDelete);
        assertFalse(repository.existsById(dni));
    }

}
