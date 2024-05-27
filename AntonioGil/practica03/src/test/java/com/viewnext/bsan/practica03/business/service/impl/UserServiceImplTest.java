package com.viewnext.bsan.practica03.business.service.impl;

import com.viewnext.bsan.practica03.business.bo.UserBo;
import com.viewnext.bsan.practica03.business.exception.BadInputDataException;
import com.viewnext.bsan.practica03.business.exception.DuplicateUniqueFieldException;
import com.viewnext.bsan.practica03.business.exception.MissingRequiredFieldException;
import com.viewnext.bsan.practica03.business.exception.ResourceNotFoundException;
import com.viewnext.bsan.practica03.persistence.repository.UserRepository;
import com.viewnext.bsan.practica03.util.mapper.ServiceLevelUserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.viewnext.bsan.practica03.sampledata.UserSampleData.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository repository;

    @Mock
    ServiceLevelUserMapper mapper;

    @InjectMocks
    UserServiceImpl service;

    @DisplayName("[UserServiceImpl] getAll (success)")
    @Test
    void givenUsers_whenGetAll_thenReturnUserList() {
        Mockito.when(repository.findAll()).thenReturn(SAMPLE_USER_LIST);
        Mockito.when(mapper.entityToBo(SAMPLE_USER)).thenReturn(SAMPLE_USER_AS_BO);
        Mockito.when(mapper.entityToBo(ANOTHER_SAMPLE_USER)).thenReturn(ANOTHER_SAMPLE_USER_AS_BO);
        Mockito.when(mapper.entityToBo(YET_ANOTHER_SAMPLE_USER)).thenReturn(YET_ANOTHER_SAMPLE_USER_AS_BO);

        final List<UserBo> expectedUsers = SAMPLE_USER_BO_LIST;

        final List<UserBo> foundUsers = service.getAll();

        assertEquals(expectedUsers.size(), foundUsers.size());
        assertTrue(foundUsers.containsAll(expectedUsers));
    }

    @DisplayName("[UserServiceImpl] getByDni (throws ResourceNotFoundException)")
    @Test
    void givenNonExistentUserDni_whenGetByDni_thenThrow() {
        final String dni = "11111111$";

        Mockito.when(repository.findById(dni)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getByDni(dni));
    }

    @DisplayName("[UserServiceImpl] getByDni (success)")
    @Test
    void givenUserDni_whenGetByDni_thenReturnUserObject() {
        final UserBo expectedUser = SAMPLE_USER_AS_BO;
        final String dni = expectedUser.getDni();

        Mockito.when(repository.findById(dni)).thenReturn(Optional.of(SAMPLE_USER));
        Mockito.when(mapper.entityToBo(SAMPLE_USER)).thenReturn(SAMPLE_USER_AS_BO);

        final UserBo foundUser = service.getByDni(dni);

        assertEquals(dni, foundUser.getDni());
        assertEquals(expectedUser.getName(), foundUser.getName());
        assertEquals(expectedUser.getSurname(), foundUser.getSurname());
        assertEquals(expectedUser.getAge(), foundUser.getAge());
    }

    @DisplayName("[UserServiceImpl] validateDni (empty dni, throws MissingRequiredFieldException)")
    @Test
    void givenEmptyDni_whenValidateDni_thenThrow() {
        final String dni = "               ";

        assertThrows(MissingRequiredFieldException.class, () -> service.validateDni(dni));
    }

    @DisplayName("[UserServiceImpl] validateDni (success)")
    @Test
    void givenDni_whenValidateDni_thenDoesNotThrow() {
        final String dni = SAMPLE_USER.getDni();

        assertDoesNotThrow(() -> service.validateDni(dni));
    }

    @DisplayName("[UserServiceImpl] validateName (empty name, throws MissingRequiredFieldException)")
    @Test
    void givenEmptyName_whenValidateName_thenThrow() {
        final String name = "\t   \n";

        assertThrows(MissingRequiredFieldException.class, () -> service.validateName(name));
    }

    @DisplayName("[UserServiceImpl] validateName (success)")
    @Test
    void givenName_whenValidateName_thenDoesNotThrow() {
        final String name = SAMPLE_USER.getName();

        assertDoesNotThrow(() -> service.validateName(name));
    }

    @DisplayName("[UserServiceImpl] validateSurname (empty surname, throws MissingRequiredFieldException)")
    @Test
    void givenEmptySurname_whenValidateSurname_thenThrow() {
        final String surname = "  \n ";

        assertThrows(MissingRequiredFieldException.class, () -> service.validateSurname(surname));
    }

    @DisplayName("[UserServiceImpl] validateSurname (success)")
    @Test
    void givenSurname_whenValidateSurname_thenDoesNotThrow() {
        final String surname = SAMPLE_USER.getSurname();

        assertDoesNotThrow(() -> service.validateSurname(surname));
    }

    @DisplayName("[UserServiceImpl] validateAge (negative age, throws BadInputDataException)")
    @Test
    void givenNegativeAge_whenValidateAge_thenThrow() {
        final int age = -33;

        assertThrows(BadInputDataException.class, () -> service.validateAge(age));
    }

    @DisplayName("[UserServiceImpl] validateName (success)")
    @Test
    void givenAge_whenValidateAge_thenDoesNotThrow() {
        final int age = SAMPLE_USER.getAge();

        assertDoesNotThrow(() -> service.validateAge(age));
    }

    @DisplayName("[UserServiceImpl] validateUser (null user, throws MissingRequiredFieldException)")
    @Test
    void givenNullUser_whenValidateActor_thenThrow() {
        assertThrows(MissingRequiredFieldException.class, () -> service.validateUser(null));
    }

    @DisplayName("[UserServiceImpl] validateActor (success)")
    @Test
    void givenUser_whenValidateActor_thenDoesNotThrow() {
        assertDoesNotThrow(() -> service.validateUser(ANOTHER_SAMPLE_USER_AS_BO));
    }

    @DisplayName("[UserServiceImpl] create (duplicate data, throws DuplicateUniqueFieldException)")
    @Test
    void givenUserWithDuplicateData_whenCreateUser_thenThrow() {
        final UserBo user = SAMPLE_USER_AS_BO;
        final String dni = user.getDni();

        Mockito.when(repository.existsById(dni)).thenReturn(true);

        assertThrows(DuplicateUniqueFieldException.class, () -> service.create(user));
    }

    @DisplayName("[UserServiceImpl] create (success)")
    @Test
    void givenUser_whenCreate_thenReturnUserObject() {
        final UserBo user = SAMPLE_USER_AS_BO;
        final String dni = user.getDni();

        Mockito.when(repository.existsById(dni)).thenReturn(false);
        Mockito.when(mapper.boToEntity(user)).thenReturn(SAMPLE_USER);
        Mockito.when(repository.save(SAMPLE_USER)).thenReturn(SAMPLE_USER);
        Mockito.when(mapper.entityToBo(SAMPLE_USER)).thenReturn(SAMPLE_USER_AS_BO);

        final UserBo savedUser = service.create(user);

        assertEquals(dni, savedUser.getDni());
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getSurname(), savedUser.getSurname());
        assertEquals(user.getAge(), savedUser.getAge());
    }

    @DisplayName("[UserServiceImpl] update (nonexistent user, throws ResourceNotFoundException)")
    @Test
    void givenNonExistentUser_whenUpdate_thenThrow() {
        final String dni = "11111111$";
        final UserBo user = UserBo.builder().dni(dni).name("JOSE").surname("DOMINGUEZ").age(35).build();

        Mockito.when(repository.existsById(dni)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.update(dni, user));
    }

    @DisplayName("[UserServiceImpl] update (success)")
    @Test
    void givenUpdatedUser_whenUpdate_thenReturnUpdatedUserObject() {
        final UserBo user = SAMPLE_USER_AS_BO_UPDATED;
        final String dni = user.getDni();

        Mockito.when(repository.existsById(dni)).thenReturn(true);
        Mockito.when(mapper.boToEntity(SAMPLE_USER_AS_BO_UPDATED)).thenReturn(SAMPLE_USER_UPDATED);
        Mockito.when(repository.save(SAMPLE_USER_UPDATED)).thenReturn(SAMPLE_USER_UPDATED);
        Mockito.when(mapper.entityToBo(SAMPLE_USER_UPDATED)).thenReturn(SAMPLE_USER_AS_BO_UPDATED);

        final UserBo savedUser = service.update(dni, user);

        assertEquals(dni, savedUser.getDni());
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getSurname(), savedUser.getSurname());
        assertEquals(user.getAge(), savedUser.getAge());
    }

    @DisplayName("[UserServiceImpl] deleteByDni (nonexistent user, throws ResourceNotFoundException)")
    @Test
    void givenNonExistentUserDni_whenDeleteByDni_thenThrow() {
        final String dni = "22222222Ç";

        Mockito.when(repository.existsById(dni)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deleteByDni(dni));
    }

    @DisplayName("[UserServiceImpl] deleteByDni (success)")
    @Test
    void givenUserDni_whenDeleteByDni_thenDoesNotThrow() {
        final String dni = YET_ANOTHER_SAMPLE_USER.getDni();

        Mockito.when(repository.existsById(dni)).thenReturn(true);

        assertDoesNotThrow(() -> service.deleteByDni(dni));
    }

}
