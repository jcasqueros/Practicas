package com.viewnext.bsan.practica04.service.impl;

import com.viewnext.bsan.practica04.bo.ActorBo;
import com.viewnext.bsan.practica04.bo.DirectorBo;
import com.viewnext.bsan.practica04.bo.FilmBo;
import com.viewnext.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnext.bsan.practica04.entity.Film;
import com.viewnext.bsan.practica04.exception.service.BadInputDataException;
import com.viewnext.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnext.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnext.bsan.practica04.exception.service.ResourceNotFoundException;
import com.viewnext.bsan.practica04.repository.FilmRepository;
import com.viewnext.bsan.practica04.repository.custom.CustomFilmRepository;
import com.viewnext.bsan.practica04.sampledata.ActorSampleData;
import com.viewnext.bsan.practica04.sampledata.DirectorSampleData;
import com.viewnext.bsan.practica04.sampledata.FilmSampleData;
import com.viewnext.bsan.practica04.sampledata.ProductionCompanySampleData;
import com.viewnext.bsan.practica04.util.mapper.ServiceLevelFilmMapper;
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
import java.util.Set;

import static com.viewnext.bsan.practica04.sampledata.FilmSampleData.SAMPLE_FILMS;
import static org.junit.jupiter.api.Assertions.*;

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
