package com.viewnext.bsan.practica04.business.service.impl;

import com.viewnext.bsan.practica04.persistence.repository.DirectorRepository;
import com.viewnext.bsan.practica04.persistence.repository.custom.CustomDirectorRepository;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelDirectorMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for {@code DirectorServiceImpl}.
 *
 * @author Antonio Gil
 */
@ExtendWith(MockitoExtension.class)
class DirectorServiceImplTest {

    @Mock
    DirectorRepository repository;

    @Mock
    CustomDirectorRepository customRepository;

    @Mock
    ServiceLevelDirectorMapper mapper;

    @InjectMocks
    DirectorServiceImpl service;

    @DisplayName("[DirectorServiceImpl] getAll (success)")
    @Test
    void xxxxx() {
        // TODO: Re-do this test
        fail("Not yet implemented");
    }

}
