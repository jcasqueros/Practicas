package com.viewnext.practica.util;

import com.viewnext.practica.businesslayer.bo.UserBO;
import com.viewnext.practica.persistencelayer.entity.User;
import com.viewnext.practica.presentationlayer.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ComponentScan(basePackages = "com.viewnext.practica.util")
public class ConverterTest {
    private User user;

    private UserBO userBO;

    private UserDTO userDTO;

    @Autowired
    Converter converter;

    @BeforeEach
    public void setup() {
        user = new User();
        userBO = new UserBO();
        userDTO = new UserDTO();
        user.setDni("12345678A");
        user.setAge(18);
        user.setName("Jhon");
        user.setSurname("Doe");
        userBO.setDni("12345678A");
        userBO.setAge(18);
        userBO.setName("Jhon");
        userBO.setSurname("Doe");
        userDTO.setDni("12345678A");
        userDTO.setAge(18);
        userDTO.setName("Jhon");
        userDTO.setSurname("Doe");
    }

    @Test
    @DisplayName("UserBO -> UserEntity")
    void givenUserBO_whenUserBOToEntity_thenReturnUserEntity() {

        User test = converter.userBOToEntity(userBO);

        assertEquals(user, test);
    }

    @Test
    @DisplayName("UserEntity -> UserBO")
    void givenUserEntity_whenUserEntityToBO_thenReturnUserBO() {

        UserBO test = converter.userEntityToBO(user);

        assertEquals(userBO, test);
    }

    @Test
    @DisplayName("UserBO-> UserDTO")
    void givenUserBO_whenUserBOToDTO_thenReturnUserDTO() {

        UserDTO test = converter.userBoToDTO(userBO);

        assertEquals(userDTO, test);
    }

    @Test
    @DisplayName("UserDTO -> UserBO")
    void givenUserDTO_whenUserDTOToBO_thenReturnUserBO() {

        UserBO test = converter.userDTOToBO(userDTO);

        assertEquals(userBO, test);
    }
}
