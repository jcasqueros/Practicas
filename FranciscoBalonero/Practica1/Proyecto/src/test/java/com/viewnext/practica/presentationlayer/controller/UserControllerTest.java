package com.viewnext.practica.presentationlayer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewnext.practica.businesslayer.bo.UserBO;
import com.viewnext.practica.businesslayer.exception.BusinessLayerException;
import com.viewnext.practica.businesslayer.exception.UserNotFoundException;
import com.viewnext.practica.businesslayer.service.UserService;
import com.viewnext.practica.presentationlayer.dto.UserDTO;
import com.viewnext.practica.util.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
    private Converter converter;
    private UserBO userBO;

    private UserDTO userDTO;

    @BeforeEach
    void setup() {
        userDTO = new UserDTO("65992744V", "Jhon", "Doe", 18);
        userBO = new UserBO("65992744V", "Jhon", "Doe", 18);
    }

    @Test
    @DisplayName("Save operation : correct case")
    void givenUserDTO_whenSave_thenReturnSavedUser() throws Exception {

        given(converter.userBoToDTO(userBO)).willReturn(userDTO);
        given(converter.userDTOToBO(userDTO)).willReturn(userBO);
        given(userService.save(any(UserBO.class))).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/v1/user/save").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.dni", is(userDTO.getDni())))
                .andExpect(jsonPath("$.name", is(userDTO.getName())))
                .andExpect(jsonPath("$.surname", is(userDTO.getSurname())))
                .andExpect(jsonPath("$.age", is(userDTO.getAge())));
    }

    @Test
    @DisplayName("GetAll operation : correct case")
    void givenNothing_whenGetAllUsers_thenReturnAllUsers() throws Exception {

        given(converter.userBoToDTO(userBO)).willReturn(userDTO);
        given(userService.getAll()).willReturn(List.of(userBO));

        ResultActions response = mockMvc.perform(get("/api/v1/user/getAllUsers"));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @DisplayName("Get user by dni operation : correct case")
    void givenDNI_whenGetUser_thenReturnFoundUser() throws Exception {

        given(converter.userBoToDTO(userBO)).willReturn(userDTO);
        given(userService.getUserByDni(userBO.getDni())).willReturn(userBO);

        ResultActions response = mockMvc.perform(get("/api/v1/user/getUser").param("dni", userBO.getDni()));

        response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.dni", is(userDTO.getDni())))
                .andExpect(jsonPath("$.name", is(userDTO.getName())))
                .andExpect(jsonPath("$.surname", is(userDTO.getSurname())))
                .andExpect(jsonPath("$.age", is(userDTO.getAge())));
    }

    @Test
    @DisplayName("Delete user operation : correct case")
    void givenDNI_whenDelete_thenDeleteUser() throws Exception {

        willDoNothing().given(userService).deleteUser(userDTO.getDni());

        ResultActions response = mockMvc.perform(delete("/api/v1/user/delete").param("dni", userBO.getDni()));

        response.andDo(print()).andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("Update user operation : correct case")
    void givenUserDTO_whenUpdateUser_thenReturnUpdatedUser() throws Exception {

        given(converter.userBoToDTO(userBO)).willReturn(userDTO);
        given(converter.userDTOToBO(userDTO)).willReturn(userBO);
        given(userService.updateUser(any(UserBO.class))).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/api/v1/user/update").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.dni", is(userDTO.getDni())))
                .andExpect(jsonPath("$.name", is(userDTO.getName())))
                .andExpect(jsonPath("$.surname", is(userDTO.getSurname())))
                .andExpect(jsonPath("$.age", is(userDTO.getAge())));
    }

    @Test
    @DisplayName("Save operation : incorrect case -> Error with business layer")
    void givenUserDTO_whenSave_thenThrowsPresentationLayerException() throws Exception {

        given(converter.userBoToDTO(userBO)).willReturn(userDTO);
        given(converter.userDTOToBO(userDTO)).willReturn(userBO);
        BDDMockito.given(userService.save(userBO)).willThrow(BusinessLayerException.class);
        ResultActions response = mockMvc.perform(post("/api/v1/user/save").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Get user by dni operation : incorrect case -> User not found")
    void givenDNI_whenGetUser_thenThrowsUserDTONotFoundException() throws Exception {

        BDDMockito.given(userService.getUserByDni(userBO.getDni())).willThrow(UserNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/api/v1/user/getUser").param("dni", userBO.getDni()));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get user by dni operation : incorrect case -> Error with business layer")
    void givenDNI_whenGetUser_thenThrowsPresentationLayerException() throws Exception {

        BDDMockito.given(userService.getUserByDni(userBO.getDni())).willThrow(BusinessLayerException.class);

        ResultActions response = mockMvc.perform(get("/api/v1/user/getUser").param("dni", userBO.getDni()));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Update user : incorrect case -> User not found")
    void givenDNI_whenUpdateUser_thenThrowsUserDTONotFoundException() throws Exception {

        given(converter.userDTOToBO(userDTO)).willReturn(userBO);
        BDDMockito.given(userService.updateUser(any(UserBO.class))).willThrow(UserNotFoundException.class);

        ResultActions response = mockMvc.perform(put("/api/v1/user/update").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Update user : incorrect case -> Error with business layer")
    void givenDNI_whenUpdateUser_thenThrowsPresentationLayerException() throws Exception {

        given(converter.userDTOToBO(userDTO)).willReturn(userBO);
        BDDMockito.given(userService.updateUser(any(UserBO.class))).willThrow(BusinessLayerException.class);

        ResultActions response = mockMvc.perform(put("/api/v1/user/update").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Delete user : incorrect case -> User not found")
    void givenDNI_whenDeleteUser_thenThrowsUserDTONotFoundException() throws Exception {

        willThrow(UserNotFoundException.class).given(userService).deleteUser("234234b");
        ResultActions response = mockMvc.perform(delete("/api/v1/user/delete").param("dni", "234234b"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete user : incorrect case -> Error with business layer")
    void givenDNI_whenDeleteUser_thenThrowsPresentationLayerException() throws Exception {

        willThrow(BusinessLayerException.class).given(userService).deleteUser("asdfasd");
        ResultActions response = mockMvc.perform(delete("/api/v1/user/delete").param("dni", "asdfasd"));

        response.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Get all users operation : incorrect case -> User not found")
    void givenNothing_whenAllGetUser_thenThrowsUserDTONotFoundException() throws Exception {

        BDDMockito.given(userService.getAll()).willThrow(UserNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/api/v1/user/getAllUsers"));

        response.andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get all users operation : incorrect case -> Error with business layer")
    void givenNothing_whenGetAllUsers_thenThrowsPresentationLayerException() throws Exception {

        BDDMockito.given(userService.getAll()).willThrow(BusinessLayerException.class);

        ResultActions response = mockMvc.perform(get("/api/v1/user/getAllUsers"));

        response.andDo(print()).andExpect(status().isBadRequest());
    }
}
