package com.viewnext.bsan.practica04.business.service.impl;

import com.viewnext.bsan.practica04.persistence.repository.FilmRepository;
import com.viewnext.bsan.practica04.persistence.repository.custom.CustomFilmRepository;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelFilmMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for {@code FilmServiceImpl}.
 *
 * @author Antonio Gil
 */
@ExtendWith(MockitoExtension.class)
class FilmServiceImplTest {

    @Mock
    FilmRepository repository;

    @Mock
    CustomFilmRepository customRepository;

    @Mock
    ServiceLevelFilmMapper mapper;

    @InjectMocks
    FilmServiceImpl service;

    @DisplayName("[FilmServiceImpl] getAll (success)")
    @Test
    void xxxxx() {
        // TODO: Re-do this test
        fail("Not yet implemented");
    }

}
