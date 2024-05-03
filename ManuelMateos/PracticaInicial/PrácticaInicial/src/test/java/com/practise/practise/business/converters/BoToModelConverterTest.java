package com.practise.practise.business.converters;

import com.practise.practise.business.bo.UserBO;
import com.practise.practise.persistence.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BoToModelConverterTest {

    @Autowired
    private BoToModelConverter boToModelConverter;

    private User user;

    private UserBO userBO;

    @BeforeEach
    void setup() {

        user = User.builder().dni("12345678A").name("prueba").surname("prueba").age(1).build();

        userBO = UserBO.builder().dni("12345678A").name("prueba").surname("prueba").age(1).build();
    }

    @DisplayName("JUnit test for convert UserBO Object to User Model Object")
    @Test
    void givenUserBO_whenUserBoToUserModel_thenReturnUserModel() {

        User convertedUser = boToModelConverter.userBoToUserModel(userBO);

        assertEquals(convertedUser, user);
    }
}
