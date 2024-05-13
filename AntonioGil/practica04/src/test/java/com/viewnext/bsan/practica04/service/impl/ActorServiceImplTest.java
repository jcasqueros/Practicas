package com.viewnext.bsan.practica04.service.impl;

import com.viewnext.bsan.practica04.bo.ActorBo;
import com.viewnext.bsan.practica04.exception.service.BadInputDataException;
import com.viewnext.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnext.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnext.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnext.bsan.practica04.repository.ActorRepository;
import com.viewnext.bsan.practica04.repository.custom.CustomActorRepository;
import com.viewnext.bsan.practica04.sampledata.ActorSampleData;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelActorMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.viewnext.bsan.practica04.sampledata.ActorSampleData.SAMPLE_ACTORS;
import static org.junit.jupiter.api.Assertions.*;

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
