package com.viewnext.bsan.practica03.dao;

import com.viewnext.bsan.practica03.config.CrudUsuariosAppConfig;
import com.viewnext.bsan.practica03.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

/**
 * Unit tests for {@code UserDao}.
 *
 * @author Antonio Gil
 */
@DataJpaTest
@Import(CrudUsuariosAppConfig.class)
class UserDaoTest {

    final UserRepository repository;

    @Autowired
    public UserDaoTest(UserRepository repository) {
        this.repository = repository;
    }

    @DisplayName("[UserDaoTest] [UNIT] getAll")
    @Test
    void givenUsers_whenGetAll_thenReturnUserList() {

    }

}
