package com.viewnext.practica.businesslayer.service;

import com.viewnext.practica.businesslayer.bo.UserBO;
import com.viewnext.practica.businesslayer.exception.BusinessLayerException;
import com.viewnext.practica.businesslayer.exception.InvalidDniException;
import com.viewnext.practica.businesslayer.exception.UserNotFoundException;
import com.viewnext.practica.businesslayer.service.impl.UserServiceImpl;
import com.viewnext.practica.persistencelayer.dao.UserDAO;
import com.viewnext.practica.persistencelayer.entity.User;
import com.viewnext.practica.persistencelayer.exception.EntityNotFoundException;
import com.viewnext.practica.persistencelayer.exception.PersistenceLayerException;
import com.viewnext.practica.util.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserBO userBO;
    private User user;

    @Mock
    Converter converter;

    @Mock
    UserDAO userDAO;

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void setup() {
        user = new User("65992744V", "Jhon", "Doe", 18);
        userBO = new UserBO("65992744V", "Jhon", "Doe", 18);
    }

    @Test
    @DisplayName("Save operation : correct case")
    void givenUserBO_whenSave_thenReturnUserBO() throws BusinessLayerException, PersistenceLayerException {

        BDDMockito.given(converter.userBOToEntity(userBO)).willReturn(user);
        BDDMockito.given(converter.userEntityToBO(user)).willReturn(userBO);
        BDDMockito.given(userDAO.save(user)).willReturn(user);

        UserBO savedUser = userService.save(userBO);

        assertThat(savedUser).isNotNull().isEqualTo(userBO);
    }

    @Test
    @DisplayName("Save operation : incorrect case -> Invalid DNI")
    void givenUserBO_whenSave_thenThrowsInvalidDNIException() throws PersistenceLayerException {

        userBO.setDni("65992741V");

        assertThrows(InvalidDniException.class, () -> userService.save(userBO));
    }

    @Test
    @DisplayName("Save operation : incorrect case -> Error with persistence layer")
    void givenUserBO_whenSave_thenThrowsBusinessLayerException() throws PersistenceLayerException {

        BDDMockito.given(converter.userBOToEntity(userBO)).willReturn(user);
        BDDMockito.given(userDAO.save(user)).willThrow(PersistenceLayerException.class);

        assertThrows(BusinessLayerException.class, () -> userService.save(userBO));
    }

    @Test
    @DisplayName("GetAll operation : correct case")
    void givenNothing_whenGetAll_thenReturnListWithAllUsersBO()
            throws PersistenceLayerException, BusinessLayerException {

        BDDMockito.given(converter.userEntityToBO(user)).willReturn(userBO);
        BDDMockito.given(userDAO.findAllUsers()).willReturn(List.of(user));

        List<UserBO> foundUsers = userService.getAll();

        assertThat(foundUsers).isNotNull();
        assertEquals(foundUsers.get(0), userBO);
        assertEquals(1, foundUsers.size());
    }

    @Test
    @DisplayName("GetAll operation : incorrect case -> Users not found")
    void givenNothing_whenGetAll_thenThrowsUserNotFoundException() throws PersistenceLayerException {

        BDDMockito.given(userDAO.findAllUsers()).willThrow(EntityNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userService.getAll());
    }

    @Test
    @DisplayName("GetAll operation : incorrect case -> Error with persistence layer")
    void givenNothing_whenGetAll_thenThrowsBusinessException() throws PersistenceLayerException {

        BDDMockito.given(userDAO.findAllUsers()).willThrow(PersistenceLayerException.class);

        assertThrows(BusinessLayerException.class, () -> userService.getAll());
    }

    @Test
    @DisplayName("Get user by dni operation : correct case")
    void givenDNI_whenGetUserByDNI_thenReturnFoundUserBO() throws PersistenceLayerException, BusinessLayerException {

        BDDMockito.given(converter.userEntityToBO(user)).willReturn(userBO);
        BDDMockito.given(userDAO.findUserByDni(user.getDni())).willReturn(user);

        UserBO foundUser = userService.getUserByDni(user.getDni());

        assertThat(foundUser).isNotNull();
        assertEquals(userBO, foundUser);
    }

    @Test
    @DisplayName("Get user by dni operation : incorrect case -> User not found")
    void givenNothing_whenGetUserByDNI_thenThrowsUserNotFoundException() throws PersistenceLayerException {

        BDDMockito.given(userDAO.findUserByDni("2342342B")).willThrow(EntityNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userService.getUserByDni("2342342B"));
    }

    @Test
    @DisplayName("Get user by dni operation : incorrect case -> Error with persistence layer")
    void givenNothing_whenGetUserByDNI_thenThrowsBusinessException() throws PersistenceLayerException {

        BDDMockito.given(userDAO.findUserByDni(null)).willThrow(PersistenceLayerException.class);

        assertThrows(BusinessLayerException.class, () -> userService.getUserByDni(null));
    }

    @Test
    @DisplayName("Delete user operation : correct case")
    void givenDNI_whenDeleteUser_thenDeleteUser() throws PersistenceLayerException, BusinessLayerException {

        BDDMockito.willDoNothing().given(userDAO).deleteUserByDni(userBO.getDni());

        userService.deleteUser(userBO.getDni());

        verify(userDAO, times(1)).deleteUserByDni(userBO.getDni());
    }

    @Test
    @DisplayName("Delete user operation : incorrect case -> User not found")
    void givenDNI_whenDeleteUser_thenThrowsUserNotFoundException() throws PersistenceLayerException {
        willThrow(EntityNotFoundException.class).given(userDAO).deleteUserByDni("2342342B");

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser("2342342B"));
    }

    @Test
    @DisplayName("Delete user operation : incorrect case -> Error with persistence layer")
    void givenDNI_whenDeleteUser_thenThrowsBusinessException() throws PersistenceLayerException {

        willThrow(PersistenceLayerException.class).given(userDAO).deleteUserByDni(null);

        assertThrows(BusinessLayerException.class, () -> userService.deleteUser(null));
    }

    @Test
    @DisplayName("Update user operation : correct case")
    void givenUserBO_whenUpdateUser_thenReturnUpdatedUserBO() throws BusinessLayerException, PersistenceLayerException {

        BDDMockito.given(converter.userBOToEntity(userBO)).willReturn(user);
        BDDMockito.given(converter.userEntityToBO(user)).willReturn(userBO);
        BDDMockito.given(userDAO.updateUser(user)).willReturn(user);

        UserBO updatedUser = userService.updateUser(userBO);

        assertThat(updatedUser).isNotNull().isEqualTo(userBO);
    }

    @Test
    @DisplayName("Update user operation : incorrect case -> Error with persistence layer")
    void givenUserBO_whenUpdateUser_thenThrowsBusinessLayerException() throws PersistenceLayerException {

        BDDMockito.given(converter.userBOToEntity(userBO)).willReturn(user);
        BDDMockito.given(userDAO.updateUser(user)).willThrow(PersistenceLayerException.class);

        assertThrows(BusinessLayerException.class, () -> userService.updateUser(userBO));
    }

    @Test
    @DisplayName("Update user operation : incorrect case -> User not found")
    void givenUserBO_whenUpdateUser_thenThrowsUserNotFoundException() throws PersistenceLayerException {

        BDDMockito.given(converter.userBOToEntity(userBO)).willReturn(user);
        BDDMockito.given(userDAO.updateUser(user)).willThrow(EntityNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userBO));
    }
}
