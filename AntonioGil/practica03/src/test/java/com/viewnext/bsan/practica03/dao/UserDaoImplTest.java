package com.viewnext.bsan.practica03.dao;

import com.viewnext.bsan.practica03.config.CrudUsuariosAppConfig;
import com.viewnext.bsan.practica03.dao.impl.UserDaoImpl;
import com.viewnext.bsan.practica03.entity.User;
import com.viewnext.bsan.practica03.exception.dao.BadUpsertException;
import com.viewnext.bsan.practica03.exception.dao.DaoLevelException;
import com.viewnext.bsan.practica03.repository.UserRepository;
import com.viewnext.bsan.practica03.sampledata.UserSampleData;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

/**
 * Unit tests for {@code UserDaoImpl}.
 *
 * @author Antonio Gil
 */
@ExtendWith(MockitoExtension.class)
@Import(CrudUsuariosAppConfig.class)
class UserDaoImplTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserDaoImpl dao;

    @DisplayName("[UserDaoImplTest] [UNIT] getAll")
    @Test
    void givenUsers_whenGetAll_thenReturnUserList() throws DaoLevelException {
        Mockito.when(repository.findAll()).thenReturn(UserSampleData.SAMPLE_USER_LIST);

        final int expectedSize = UserSampleData.SAMPLE_USER_LIST.size();
        final List<User> foundUsers = dao.getAll();

        assertEquals(expectedSize, foundUsers.size());
        assertTrue(foundUsers.containsAll(UserSampleData.SAMPLE_USER_LIST));
    }

    @DisplayName("[UserDaoImplTest] [UNIT] getByDni (null DNI)")
    @Test
    void givenNullDni_whenGetByDni_thenReturnEmpty() throws DaoLevelException {
        final Optional<User> foundUser = dao.getByDni(null);

        assertTrue(foundUser.isEmpty());
    }

    @DisplayName("[UserDaoImplTest] [UNIT] getByDni (nonexistent DNI)")
    @Test
    void givenNonExistentDni_whenGetByDni_thenReturnEmpty() throws DaoLevelException {
        final String dni = "XXXXXX";
        Mockito.when(repository.findById(dni)).thenReturn(Optional.empty());

        final Optional<User> foundUser = dao.getByDni(dni);

        assertTrue(foundUser.isEmpty());
    }

    @DisplayName("[UserDaoImplTest] [UNIT] getByDni (existing DNI)")
    @Test
    void givenDni_whenGetByDni_thenReturnUserObject() throws DaoLevelException {
        final User expectedUser = UserSampleData.YET_ANOTHER_SAMPLE_USER;
        final String dni = expectedUser.getDni();
        Mockito.when(repository.findById(dni)).thenReturn(Optional.of(expectedUser));

        final User foundUser = dao.getByDni(dni).orElseThrow();

        assertEquals(dni, foundUser.getDni());
        assertEquals(expectedUser.getName(), foundUser.getName());
        assertEquals(expectedUser.getSurname(), foundUser.getSurname());
        assertEquals(expectedUser.getAge(), foundUser.getAge());
    }

    @DisplayName("[UserDaoImplTest] [UNIT] create (integrity constraint violation)")
    @Test
    void givenDuplicateUser_whenCreate_thenThrowBadUpsertException() {
        final User user = UserSampleData.SAMPLE_USER;
        Mockito.when(repository.save(user))
                .thenThrow(new DataIntegrityViolationException("Uniqueness constraint violated: M_USER.M_DNI"));

        BadUpsertException exception = assertThrows(BadUpsertException.class, () -> dao.create(user));
        assertEquals("UserDao#create failed due to integrity constraint violation(s)", exception.getMessage());
    }

    @DisplayName("[UserDaoImplTest] [UNIT] create (success)")
    @Test
    void givenUser_whenCreate_thenUserIsSaved() throws DaoLevelException {
        final User user = UserSampleData.SAMPLE_USER;
        Mockito.when(repository.save(user)).thenReturn(user);

        final User savedUser = dao.create(user);

        assertEquals(user.getDni(), savedUser.getDni());
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getSurname(), savedUser.getSurname());
        assertEquals(user.getAge(), savedUser.getAge());
    }

    @DisplayName("[UserDaoImplTest] [UNIT] update (nonexistent user)")
    @Test
    void givenNonExistentUser_whenUpdate_thenThrowBadUpsertException() {
        final String dni = "XXXXXX";
        final User user = User.builder().dni(dni).name("JOSEFINA").surname("DOMINGUEZ").age(35).build();
        Mockito.when(repository.existsById(dni)).thenReturn(false);

        BadUpsertException exception = assertThrows(BadUpsertException.class, () -> dao.update(user));
        assertEquals("UserDao#update failed: Cannot update a nonexistent entity", exception.getMessage());
    }

    @DisplayName("[UserDaoImplTest] [UNIT] update (existing user)")
    @Test
    void givenUser_whenUpdate_thenUserIsUpdated() throws DaoLevelException {
        final String dni = UserSampleData.SAMPLE_USER.getDni();
        final User user = User.builder().dni(dni).name("JOSEFINA").surname("DOMINGUEZ").age(35).build();
        Mockito.when(repository.existsById(dni)).thenReturn(true);
        Mockito.when(repository.save(user)).thenReturn(user);

        final User updatedUser = dao.update(user);

        assertEquals(dni, updatedUser.getDni());
        assertEquals(user.getName(), updatedUser.getName());
        assertEquals(user.getSurname(), updatedUser.getSurname());
        assertEquals(user.getAge(), updatedUser.getAge());
    }

    @DisplayName("[UserDaoImplTest] [UNIT] deleteByDni (existing user)")
    @Test
    void givenDni_whenDeleteByDni_thenNoExceptionIsThrown() {
        final String dni = UserSampleData.ANOTHER_SAMPLE_USER.getDni();

        assertDoesNotThrow(() -> dao.deleteByDni(dni));
    }

}
