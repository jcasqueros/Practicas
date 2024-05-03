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
class DtoToBoConverterTest {

    @Autowired
    private DtoToBoConverter dtoToBoConverter;

    private UserBO userBO;

    private UserDTO userDTO;

    @BeforeEach
    void setup() {

        userBO = UserBO.builder().dni("12345678A").name("prueba").surname("prueba").age(1).build();

        userDTO = UserDTO.builder().dni("12345678A").name("prueba").surname("prueba").age(1).build();
    }

    @DisplayName("JUnit test for convert UserDTO Object to UserBO Object")
    @Test
    void givenUserDTO_whenUserDtoToUserBO_thenReturnUserBO() {

        UserBO convertedUser = dtoToBoConverter.userDtoToUserBo(userDTO);

        assertEquals(convertedUser, userBO);
    }
}
