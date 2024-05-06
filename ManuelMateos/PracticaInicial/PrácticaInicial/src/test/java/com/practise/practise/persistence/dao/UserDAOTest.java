package com.practise.practise.persistence.dao;

import com.practise.practise.exceptions.*;
import com.practise.practise.persistence.dao.impl.UserDAOImpl;
import com.practise.practise.persistence.models.User;
import com.practise.practise.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDAOTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDAOImpl userDAO;

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
    void givenUserObject_whenSave_thenThrowDuplicatedException() throws DataAccessException {

        userDAO.save(user);

        given(userRepository.existsById(user.getDni())).willReturn(true);

        assertThrows(DuplicatedIdException.class, () -> userDAO.save(user));
    }

    @DisplayName("JUnit test for save user operation - negative")
    @Test
    void givenUserObject_whenSave_thenThrowUpsertException() throws DataAccessException {

        doThrow(new DataIntegrityViolationException("")).when(userRepository).saveUser("dni", "name", "surname", 25);

        UpsertException exception = assertThrows(UpsertException.class,
                () -> userDAO.save(new User("dni", "name", "surname", 25)));
        assertEquals("Insert error", exception.getMessage());
    }

    @DisplayName("JUnit test for delete user by his id - positive")
    @Test
    void givenIdUser_whenDelete_thenThrowEmptyException() throws DataAccessException {
        when(userRepository.existsById("dni")).thenReturn(true);
        doNothing().when(userRepository).deleteUserById("dni");

        userDAO.deleteById("dni");

        verify(userRepository, times(1)).deleteUserById("dni");

    }

    @DisplayName("JUnit test for delete user by his id - negative")
    @Test
    void givenIdUser_whenDelete_thenThrowEntityNotFoundException() throws DataAccessException {

        assertThrows(EntityNotFoundException.class, () -> userDAO.deleteById("124A"));
    }

    @DisplayName("JUnit test for update user operation - positive")
    @Test
    void givenUserObject_whenUpdate_thenReturnUpdateUserObject() throws DataAccessException {
        User user = new User("dni", "name", "surname", 25);
        when(userRepository.findUserById("dni")).thenReturn(Optional.of(user));

        User savedUser = userDAO.findById(user.getDni());
        savedUser.setName("update");
        savedUser.setSurname("update");
        savedUser.setAge(100);
        User updatedUser = userDAO.update(savedUser);

        assertThat(updatedUser).isNotNull();
        assertEquals(savedUser, updatedUser);
    }

    @DisplayName("JUnit test for update user operation - negative")
    @Test
    void givenUserObject_whenUpdate_thenThrowException() throws DataAccessException {
        User user = new User("dni", "name", "surname", 25);

        doThrow(new DataIntegrityViolationException("")).when(userRepository).updateUser("dni", "name", "surname", 25);

        UpsertException exception = assertThrows(UpsertException.class,
                () -> userDAO.update(new User("dni", "name", "surname", 25)));
        assertEquals("Update error", exception.getMessage());
    }

    @DisplayName("JUnit test for get all users operation - positive")
    @Test
    void givenNothing_whenFindAll_thenReturnUserList() throws DataAccessException {

        List<User> users = Arrays.asList(new User("dni1", "name1", "surname1", 25),
                new User("dni2", "name2", "surname2", 30));
        when(userRepository.findAllUsers()).thenReturn(users);

        List<User> savedUsers = userDAO.findAll();

        assertEquals(users, savedUsers);
    }

    @DisplayName("JUnit test for get all users operation - negative")
    @Test
    void givenNothing_whenFindAll_thenReturnException() throws DataAccessException {

        assertThrows(EmptyException.class, () -> userDAO.findAll());
    }

    @DisplayName("JUnit test for get user by id operation - positive")
    @Test
    void givenUserObject_whenFindById_thenReturnUserObject() throws DataAccessException {
        User user = new User("dni", "name", "surname", 25);
        when(userRepository.findUserById("dni")).thenReturn(Optional.of(user));

        User savedUser = userDAO.findById("dni");

        assertEquals(user, savedUser);
    }

    @DisplayName("JUnit test for get user by id operation - negative")
    @Test
    void givenUserObject_whenFindById_thenThrowException() throws DataAccessException {
        when(userRepository.findUserById("dni")).thenReturn(Optional.empty());

        EmptyException exception = assertThrows(EmptyException.class, () -> userDAO.findById("dni"));
        assertEquals("User not found", exception.getMessage());
    }
}
