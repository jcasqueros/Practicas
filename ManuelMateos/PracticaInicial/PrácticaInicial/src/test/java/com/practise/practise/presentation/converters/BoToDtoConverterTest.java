package com.practise.practise.presentation.converters;

import com.practise.practise.business.bo.UserBO;
import com.practise.practise.presentation.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BoToDtoConverterTest {

    @Autowired
    private BoToDtoConverter boToDtoConverter;

    private UserBO userBO;

    private UserDTO userDTO;

    @BeforeEach
    void setup() {

        userBO = UserBO.builder().dni("12345678A").name("prueba").surname("prueba").age(1).build();

        userDTO = UserDTO.builder().dni("12345678A").name("prueba").surname("prueba").age(1).build();
    }

    @DisplayName("JUnit test for convert UserBO Object to UserDTO Object")
    @Test
    void givenUserBO_whenUserBoToUserDto_thenReturnUserDTO() {

        UserDTO convertedUser = boToDtoConverter.userBoToUserDto(userBO);

        assertEquals(convertedUser, userDTO);
    }
}
