package com.viewnext.bsan.practica04.service.impl;

import com.viewnext.bsan.practica04.bo.DirectorBo;
import com.viewnext.bsan.practica04.entity.Director;
import com.viewnext.bsan.practica04.exception.service.BadInputDataException;
import com.viewnext.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnext.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnext.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnext.bsan.practica04.repository.DirectorRepository;
import com.viewnext.bsan.practica04.repository.custom.CustomDirectorRepository;
import com.viewnext.bsan.practica04.sampledata.DirectorSampleData;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelDirectorMapper;
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

import static com.viewnext.bsan.practica04.sampledata.DirectorSampleData.SAMPLE_DIRECTORS;
import static org.junit.jupiter.api.Assertions.*;

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
