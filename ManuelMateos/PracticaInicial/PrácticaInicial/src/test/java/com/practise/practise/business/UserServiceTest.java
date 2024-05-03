package com.practise.practise.business;

import com.practise.practise.business.bo.UserBO;
import com.practise.practise.business.converters.BoToModelConverter;
import com.practise.practise.business.converters.ModelToBoConverter;
import com.practise.practise.business.services.impl.UserServiceImpl;
import com.practise.practise.exceptions.DataAccessException;
import com.practise.practise.exceptions.ServiceException;
import com.practise.practise.persistence.dao.UserDAO;
import com.practise.practise.persistence.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private ModelToBoConverter modelToBoConverter;

    @Mock
    private BoToModelConverter boToModelConverter;

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    private UserBO userBO;

    @BeforeEach
    void setup() {

        user = User.builder().dni("12345678A").name("prueba").surname("prueba").age(1).build();

        userBO = UserBO.builder().dni("12345678A").name("prueba").surname("prueba").age(1).build();
    }

    @DisplayName("JUnit test for save a user - positive")
    @Test
    void givenUserBoObject_whenSave_thenReturnVoid() throws DataAccessException, ServiceException {

        given(modelToBoConverter.userModelToUserBo(user)).willReturn(userBO);
        given(boToModelConverter.userBoToUserModel(userBO)).willReturn(user);
        given(userDAO.save(boToModelConverter.userBoToUserModel(userBO))).willReturn(user);

        UserBO savedUserBO = userService.save(userBO);

        assertThat(savedUserBO).isNotNull();
        assertEquals(savedUserBO, userBO);
    }

    @DisplayName("JUnit test for save a user - negative")
    @Test
    void givenUserBoObject_whenSave_thenThrowException() throws DataAccessException, ServiceException {
        given(userDAO.save(boToModelConverter.userBoToUserModel(userBO))).willThrow(DataAccessException.class);

        assertThrows(ServiceException.class, () -> userService.save(userBO));
    }

    @DisplayName("JUnit test for get all users - positive")
    @Test
    void givenNothing_whenFindAll_thenReturnUserBOList() throws DataAccessException, ServiceException {
        given(modelToBoConverter.userModelToUserBo(user)).willReturn(userBO);
        given(userDAO.findAll()).willReturn(List.of(user));

        List<UserBO> userBOList = userService.findAll();

        assertThat(userBOList).isNotEmpty();
        assertEquals(1, userBOList.size());
    }

    @DisplayName("JUnit test for get all users - negative")
    @Test
    void givenNothing_whenFindAll_thenThrowException() throws DataAccessException, ServiceException {
        given(userDAO.findAll()).willThrow(DataAccessException.class);

        assertThrows(ServiceException.class, () -> userService.findAll());
    }

    @DisplayName("JUnit test for get a user by his id - positive")
    @Test
    void givenUserId_whenFindById_thenReturnUserBO() throws DataAccessException, ServiceException {
        given(modelToBoConverter.userModelToUserBo(user)).willReturn(userBO);
        given(userDAO.findById(user.getDni())).willReturn(user);

        UserBO savedUserBO = userService.findById(userBO.getDni());

        assertThat(savedUserBO).isNotNull();
        assertEquals(savedUserBO, userBO);
    }

    @DisplayName("JUnit test for get a user by his id - negative")
    @Test
    void givenUserId_whenFindById_thenThrowException() throws DataAccessException, ServiceException {
        given(userDAO.findById(user.getDni())).willThrow(DataAccessException.class);

        assertThrows(ServiceException.class, () -> userService.findById(userBO.getDni()));
    }

    @DisplayName("JUnit test for update a user - positive")
    @Test
    void givenUserBO_whenUpdate_thenReturnUpdatedUserBO() throws DataAccessException, ServiceException {
        given(modelToBoConverter.userModelToUserBo(user)).willReturn(userBO);
        given(boToModelConverter.userBoToUserModel(userBO)).willReturn(user);
        given(userDAO.update(boToModelConverter.userBoToUserModel(userBO))).willReturn(user);
        given(userDAO.findById("12345678A")).willReturn(user);

        UserBO updatedUserBO = userBO;
        updatedUserBO.setName("update");
        updatedUserBO.setSurname("update");
        updatedUserBO.setAge(100);
        UserBO savedUserBO = userService.update(updatedUserBO);

        assertThat(savedUserBO).isNotNull();
        assertEquals(savedUserBO, updatedUserBO);
    }

    @DisplayName("JUnit test for update a user - negative")
    @Test
    void givenUserBO_whenUpdate_thenthrowException() throws DataAccessException, ServiceException {
        given(modelToBoConverter.userModelToUserBo(user)).willReturn(userBO);
        given(boToModelConverter.userBoToUserModel(userBO)).willReturn(user);
        given(userDAO.findById("12345678A")).willReturn(user);
        given(userDAO.update(boToModelConverter.userBoToUserModel(userBO))).willThrow(DataAccessException.class);

        assertThrows(ServiceException.class, () -> userService.update(userBO));
    }

    @DisplayName("JUnit test for delete a user - positive")
    @Test
    void givenUserId_whenDelete_thenDelete() throws DataAccessException, ServiceException {

        willDoNothing().given(userDAO).deleteById(user.getDni());

        userService.delete(userBO.getDni());

        verify(userDAO, times(1)).deleteById(user.getDni());
    }

    @DisplayName("JUnit test for delete a user - negative")
    @Test
    void givenUserId_whenDelete_thenThrowException() throws DataAccessException, ServiceException {
        willThrow(DataAccessException.class).given(userDAO).deleteById(user.getDni());

        assertThrows(ServiceException.class, () -> userService.delete(userBO.getDni()));
    }
}
