package com.viewnext.practica.persistencelayer.repository;

import com.viewnext.practica.persistencelayer.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    private User user;

    @BeforeEach
    public void setup() {
        user = new User("12345678A", "TestName", "TestSurname", 18);
    }

    @Test
    @DisplayName("saveUser operation and findUserByDni operation with @Query method")
    void givenUserObject_whenSaveUser_thenSavedUser() {

        userRepository.saveUser(user.getDni(), user.getName(), user.getSurname(), user.getAge());

        Optional<User> savedUser = userRepository.findUserByDni(user.getDni());

        assertTrue(savedUser.isPresent());
        assertEquals(savedUser.get(), user);

    }

    @Test
    @DisplayName("findAllUsers operation with @Query method")
    void givenUsersList_whenFindAll_theReturnUsersList() {

        userRepository.saveUser(user.getDni(), user.getName(), user.getSurname(), user.getAge());

        List<User> foundUsers = userRepository.findAllUsers();

        assertFalse(foundUsers.isEmpty());
        assertEquals(foundUsers.get(0), user);

    }

    @Test
    @DisplayName("deleteUserByDni operation with @Query method")
    void givenUser_whenDeleteByDni_thenDeleteUser() {

        userRepository.saveUser(user.getDni(), user.getName(), user.getSurname(), user.getAge());

        int test = userRepository.deleteUserByDni(user.getDni());

        assertEquals(1, test);

    }

    @Test
    @DisplayName("updateUser operation with @Query method")
    void givenUser_when_updateUser_thenUpdatedUser() {

        userRepository.saveUser(user.getDni(), user.getName(), user.getSurname(), user.getAge());

        int test = userRepository.updateUser(user.getDni(), "updated", "updated", 55);

        assertEquals(1, test);
    }
}
