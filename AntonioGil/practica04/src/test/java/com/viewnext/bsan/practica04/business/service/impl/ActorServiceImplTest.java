package com.viewnext.bsan.practica04.business.service.impl;

import com.viewnext.bsan.practica04.persistence.repository.ActorRepository;
import com.viewnext.bsan.practica04.persistence.repository.custom.CustomActorRepository;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelActorMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for {@code ActorServiceImpl}.
 *
 * @author Antonio Gil
 */
@ExtendWith(MockitoExtension.class)
class ActorServiceImplTest {

    @Mock
    ActorRepository repository;

    @Mock
    CustomActorRepository customRepository;

    @Mock
    ServiceLevelActorMapper mapper;

    @InjectMocks
    ActorServiceImpl service;

    @DisplayName("[ActorServiceImpl] getAll (success)")
    @Test
    void xxxxx() {
        // TODO: Re-do this test
        fail("Not yet implemented");
    }

}
