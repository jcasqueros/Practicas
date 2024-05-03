package com.viewnext.practica.persistencelayer.dao;

import com.viewnext.practica.persistencelayer.entity.User;
import com.viewnext.practica.persistencelayer.exception.PersistenceLayerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ComponentScan(basePackages = "com.viewnext.practica.persistencelayer.dao")
class UserDAOTest {
    @Autowired
    private UserDAO userDao;

    private User user, user2, duplicatedDniUser;

    @BeforeEach
    public void setup() {
        user = new User();
        user2 = new User();
        duplicatedDniUser = new User();
        user.setDni("12345678A");
        user.setAge(18);
        user.setName("Jhon");
        user.setSurname("Doe");
        user2.setDni("87654321B");
        user2.setName("Casillas");
        user2.setSurname("Casillas");
        user2.setAge(18);
        duplicatedDniUser.setDni("12345678A");
        duplicatedDniUser.setAge(20);
        duplicatedDniUser.setName("Lionel");
        duplicatedDniUser.setSurname("Messi");
    }

    @Test
    @DisplayName("findUserByDNI operation")
    void givenDni_whenFindUserByDni_theReturnUser() throws PersistenceLayerException {

        User savedUser = userDao.save(user);

        User foundUser = userDao.findUserByDni(user.getDni());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser).isEqualTo(savedUser);
    }

    @Test
    @DisplayName("save operation")
    void givenUserObject_whenSave_theReturnSavedUser() throws PersistenceLayerException {

        User savedUser = userDao.save(user);

        assertThrows(PersistenceLayerException.class, () -> userDao.save(duplicatedDniUser));
        assertThat(savedUser).isNotNull();
        assertEquals(savedUser, user);
    }

    @Test
    @DisplayName("findAllUsers operation")
    void givenUsersList_whenFindAllUsers_theReturnUsersList() throws PersistenceLayerException {

        userDao.save(user);
        userDao.save(user2);

        List<User> users = userDao.findAllUsers();

        assertThat(users).isNotNull();
        assertEquals(2, users.size());
    }

    @Test
    @DisplayName("updateUser operation")
    void givenUser_whenUpdateUser_theReturnUpdatedUser() throws PersistenceLayerException {

        userDao.save(user);
        User updatedUser = new User();
        updatedUser.setDni(user.getDni());
        updatedUser.setName("test");
        updatedUser.setSurname("test");
        updatedUser.setAge(34);

        User savedUpdatedUser = userDao.updateUser(updatedUser);

        assertThat(savedUpdatedUser).isNotNull();
        assertEquals(savedUpdatedUser, updatedUser);
    }

    @Test
    @DisplayName("deleteUserByDni operation")
    void givenUser_whenDeleteUserByDni_thenDeleteUser() throws PersistenceLayerException {

        userDao.save(user);

        userDao.deleteUserByDni(user.getDni());

        assertThrows(PersistenceLayerException.class, () -> userDao.findUserByDni(user.getDni()));
    }

}

