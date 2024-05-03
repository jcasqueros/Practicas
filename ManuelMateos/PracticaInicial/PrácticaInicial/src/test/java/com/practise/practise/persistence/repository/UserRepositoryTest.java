package com.practise.practise.persistence.repository;

import com.practise.practise.persistence.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setup() {

        user = User.builder().dni("12345678A").name("prueba").surname("prueba").age(1).build();
    }

    @DisplayName("JUnit test for save user operation")
    @Test
    void givenUserObject_whenSave_thenReturnSavedUser() {

        userRepository.saveUser(user.getDni(), user.getName(), user.getSurname(), user.getAge());

        User userDB = userRepository.findById(user.getDni()).get();

        assertThat(userDB).isNotNull();
        assertEquals(userDB, user);
    }

    @DisplayName("JUnit test for get all users operation")
    @Test
    void givenUserList_whenFindAll_thenReturnUserList() {

        userRepository.saveUser(user.getDni(), user.getName(), user.getSurname(), user.getAge());

        List<User> usersDB = userRepository.findAll();

        assertThat(usersDB).isNotNull();
        assertEquals(1, usersDB.size());
    }

    @DisplayName("JUnit test for get user by id operation")
    @Test
    void givenUserObject_whenFindById_thenReturnUserObject() {

        userRepository.saveUser(user.getDni(), user.getName(), user.getSurname(), user.getAge());

        User userDB = userRepository.findById(user.getDni()).get();

        assertThat(userDB).isNotNull();
        assertEquals(userDB, user);
    }

    @DisplayName("JUnit test for update user operation")
    @Test
    void givenUserObject_whenUpdate_thenReturnUpdateUserObject() {

        userRepository.save(user);

        User savedUser = userRepository.findById(user.getDni()).get();
        savedUser.setName("update");
        savedUser.setSurname("update");
        savedUser.setAge(100);
        userRepository.updateUser(savedUser.getDni(), savedUser.getName(), savedUser.getSurname(), savedUser.getAge());

        User userDB = userRepository.findById(user.getDni()).get();

        assertEquals(userDB, savedUser);
    }

    @DisplayName("JUnit test for delete user operation")
    @Test
    void givenIdUser_whenDelete_thenRemoveUser() {

        userRepository.saveUser(user.getDni(), user.getName(), user.getSurname(), user.getAge());

        userRepository.delete(user);
        Optional<User> userOptional = userRepository.findById(user.getDni());

        assertThat(userOptional).isEmpty();
    }
}
