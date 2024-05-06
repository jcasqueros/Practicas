package com.practise.practise.presentation.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practise.practise.business.bo.UserBO;
import com.practise.practise.business.services.UserService;
import com.practise.practise.exceptions.ServiceException;
import com.practise.practise.presentation.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private BoToDtoConverter boToDtoConverter;

    @MockBean
    private DtoToBoConverter dtoToBoConverter;

    private UserBO userBO;

    private UserDTO userDTO;

    @BeforeEach
    void setup() {

        userBO = UserBO.builder().dni("12345678A").name("prueba").surname("prueba").age(1).build();

        userDTO = UserDTO.builder().dni("12345678A").name("prueba").surname("prueba").age(1).build();
    }

    @DisplayName("Junit test for save a UserDTO Object - positive")
    @Test
    void givenUserDTOObject_whenSave_thenReturnSavedUser() throws Exception {
        given(boToDtoConverter.userBoToUserDto(userBO)).willReturn(userDTO);
        given(dtoToBoConverter.userDtoToUserBo(userDTO)).willReturn(userBO);
        given(userService.save(any(UserBO.class))).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/users/save").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.dni", is(userDTO.getDni())))
                .andExpect(jsonPath("$.name", is(userDTO.getName())))
                .andExpect(jsonPath("$.surname", is(userDTO.getSurname())))
                .andExpect(jsonPath("$.age", is(userDTO.getAge())));

    }

    @DisplayName("Junit test for save a UserDTO Object - negative")
    @Test
    void givenUserDTOObject_whenSave_thenThrowException() throws Exception {
        given(boToDtoConverter.userBoToUserDto(userBO)).willReturn(userDTO);
        given(dtoToBoConverter.userDtoToUserBo(userDTO)).willReturn(userBO);
        given(userService.save(any(UserBO.class))).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(post("/users/save").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get all users - positive")
    @Test
    void givenNothing_whenFindAll_thenReturnUserDTOList() throws Exception {
        given(boToDtoConverter.userBoToUserDto(userBO)).willReturn(userDTO);
        given(dtoToBoConverter.userDtoToUserBo(userDTO)).willReturn(userBO);
        List<UserBO> usersBO = new ArrayList<>();
        usersBO.add(userBO);
        given(userService.findAll()).willReturn(usersBO);

        ResultActions response = mockMvc.perform(get("/users/findAll"));

        response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(usersBO.size())));
    }

    @DisplayName("Junit test for get all users - negative")
    @Test
    void givenNothing_whenFindAll_thenThrowException() throws Exception {
        given(boToDtoConverter.userBoToUserDto(userBO)).willReturn(userDTO);
        given(dtoToBoConverter.userDtoToUserBo(userDTO)).willReturn(userBO);
        given(userService.findAll()).willThrow(new ServiceException(""));

        ResultActions response = mockMvc.perform(get("/users/findAll"));

        response.andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for get a user by his id - positive")
    @Test
    void givenUserId_whenGetById_thenUserDTO() throws Exception {
        given(boToDtoConverter.userBoToUserDto(userBO)).willReturn(userDTO);
        given(dtoToBoConverter.userDtoToUserBo(userDTO)).willReturn(userBO);
        given(userService.findById(userBO.getDni())).willReturn(userBO);

        ResultActions response = mockMvc.perform(get("/users/getById/{id}", userBO.getDni()));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.dni", is(userDTO.getDni())))
                .andExpect(jsonPath("$.name", is(userDTO.getName())))
                .andExpect(jsonPath("$.surname", is(userDTO.getSurname())))
                .andExpect(jsonPath("$.age", is(userDTO.getAge())));
    }

    @DisplayName("Junit test for get a user by his id - negative")
    @Test
    void givenUserId_whenGetById_thenThrowException() throws Exception {
        given(boToDtoConverter.userBoToUserDto(userBO)).willReturn(userDTO);
        given(dtoToBoConverter.userDtoToUserBo(userDTO)).willReturn(userBO);
        given(userService.findById(userBO.getDni())).willThrow(new ServiceException("error"));

        ResultActions response = mockMvc.perform(get("/users/getById/{id}", userBO.getDni()));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for update a user - positive")
    @Test
    void givenUserDTO_whenUpdate_thenUpdatedUserDTO() throws Exception {
        given(boToDtoConverter.userBoToUserDto(userBO)).willReturn(userDTO);
        given(dtoToBoConverter.userDtoToUserBo(userDTO)).willReturn(userBO);
        given(userService.findById(userBO.getDni())).willReturn(userBO);
        given(userService.update(any(UserBO.class))).willAnswer((invocation -> invocation.getArgument(0)));

        UserDTO updatedUserDTO = userDTO;
        updatedUserDTO.setName("updated");
        updatedUserDTO.setSurname("updated");
        updatedUserDTO.setAge(100);

        ResultActions response = mockMvc.perform(put("/users/update").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUserDTO)));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.dni", is(userDTO.getDni())))
                .andExpect(jsonPath("$.name", is("updated"))).andExpect(jsonPath("$.surname", is("updated")))
                .andExpect(jsonPath("$.age", is(100)));
    }

    @DisplayName("Junit test for update a user - negative")
    @Test
    void givenUserDTO_whenUpdate_thenThrowException() throws Exception {
        given(boToDtoConverter.userBoToUserDto(userBO)).willReturn(userDTO);
        given(dtoToBoConverter.userDtoToUserBo(userDTO)).willReturn(userBO);
        given(userService.findById(userBO.getDni())).willReturn(userBO);
        given(userService.update(any(UserBO.class))).willThrow(new ServiceException(""));

        UserDTO updatedUserDTO = userDTO;
        updatedUserDTO.setName("updated");
        updatedUserDTO.setSurname("updated");
        updatedUserDTO.setAge(100);

        ResultActions response = mockMvc.perform(put("/users/update").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUserDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @DisplayName("Junit test for delete a user by his id - positive")
    @Test
    void givenUserID_whenDeleteById_thenDelete() throws Exception {
        willDoNothing().given(userService).delete(userBO.getDni());

        ResultActions response = mockMvc.perform(delete("/users/deleteById").param("dni", userDTO.getDni()));

        response.andDo(print()).andExpect(status().isNoContent());
    }

    @DisplayName("Junit test for delete a user by his id - negative")
    @Test
    void givenUserID_whenDeleteById_thenThrowException() throws Exception {
        willThrow(new ServiceException("")).given(userService).delete(userBO.getDni());

        ResultActions response = mockMvc.perform(delete("/users/deleteById").param("dni", userDTO.getDni()));

        response.andDo(print()).andExpect(status().isBadRequest());
    }
}
