package com.practise.practise.persistence.dao;

import com.practise.practise.exceptions.DataAccessException;
import com.practise.practise.exceptions.DuplicatedIdException;
import com.practise.practise.exceptions.EmptyException;
import com.practise.practise.exceptions.EntityNotFoundException;
import com.practise.practise.persistence.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ComponentScan(basePackages = "com.practise.practise.persistence.dao")
class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    private User user;

    @BeforeEach
    void setup() {

        user = User.builder().dni("12345678A").name("prueba").surname("prueba").age(1).build();
    }

    @DisplayName("JUnit test for save user operation - positive")
    @Test
    void givenUserObject_whenSave_thenReturnSavedUser() throws DataAccessException {

        User savedUser = userDAO.save(user);

        assertEquals(savedUser, user);
    }

    @DisplayName("JUnit test for save user operation - negative")
    @Test
    void givenUserObject_whenSave_thenThrowException() throws DataAccessException {

        User savedUser = userDAO.save(user);

        assertThrows(DuplicatedIdException.class, () -> userDAO.save(user));
    }

    @DisplayName("JUnit test for delete user by his id - positive")
    @Test
    void givenIdUser_whenDelete_thenRemoveUser() throws DataAccessException {
        userDAO.save(user);

        userDAO.deleteById(user.getDni());

        assertThrows(EmptyException.class, () -> {
            userDAO.findById(user.getDni());
        });
    }

    @DisplayName("JUnit test for delete user by his id - negative")
    @Test
    void givenIdUser_whenDelete_thenThrowException() throws DataAccessException {

        assertThrows(EntityNotFoundException.class, () -> userDAO.deleteById("124A"));
    }

    @DisplayName("JUnit test for update user operation")
    @Test
    void givenUserObject_whenUpdate_thenReturnUpdateUserObject() throws DataAccessException {

        userDAO.save(user);

        User savedUser = userDAO.findById(user.getDni());
        savedUser.setName("update");
        savedUser.setSurname("update");
        savedUser.setAge(100);
        User updatedUser = userDAO.update(savedUser);

        assertThat(updatedUser).isNotNull();
        assertEquals(savedUser, updatedUser);
    }

    @DisplayName("JUnit test for get all users operation - positive")
    @Test
    void givenNothing_whenFindAll_thenReturnUserList() throws DataAccessException {

        userDAO.save(user);

        List<User> usersDB = userDAO.findAll();

        assertThat(usersDB).isNotNull();
        assertEquals(1, usersDB.size());
    }

    @DisplayName("JUnit test for get all users operation - negative")
    @Test
    void givenNothing_whenFindAll_thenReturnException() throws DataAccessException {

        assertThrows(EmptyException.class, () -> userDAO.findAll());
    }

    @DisplayName("JUnit test for get user by id operation")
    @Test
    void givenUserObject_whenFindById_thenReturnUserObject() throws DataAccessException {

        userDAO.save(user);
        User userDB = userDAO.findById(user.getDni());

        assertThat(userDB).isNotNull();
        assertEquals(userDB, user);
    }
}
