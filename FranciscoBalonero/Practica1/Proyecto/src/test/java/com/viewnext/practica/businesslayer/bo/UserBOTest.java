package com.viewnext.practica.businesslayer.bo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ComponentScan(basePackages = "com.viewnext.practica.businesslayer.bo")
class UserBOTest {
    
    @DisplayName("Valid DNI -> TRUE")
    @Test
    void givenUserBO_whenValidateDNI_thenReturnTrue() {

        UserBO userBO = new UserBO();
        userBO.setDni("65992744V");

        assertTrue(userBO.validateDNI());
    }

    @DisplayName("Invalid DNI -> FALSE")
    @Test
    void givenUserBO_whenValidateDNI_thenReturnFalse() {

        UserBO userBO = new UserBO();
        userBO.setDni("65999744V");

        assertFalse(userBO.validateDNI());
    }
}
