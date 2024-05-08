package com.pracs.films.presentation.converters;

import com.pracs.films.bussiness.bo.*;
import com.pracs.films.presentation.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DtoToBoConverterTest {

    @Autowired
    private DtoToBoConverter DtoToBoConverter;

    private ActorBO actorBO;

    private ActorDtoIn actorDtoIn;

    private DirectorBO directorBO;

    private DirectorDtoIn directorDtoIn;

    private ProducerBO producerBO;

    private ProducerDtoIn producerDtoIn;

    private SerieBO serieBO;

    private SerieDtoIn serieDtoIn;

    private FilmBO filmBO;

    private FilmDtoIn filmDtoIn;

    @BeforeEach
    void setup() {

        actorBO = new ActorBO();
        actorBO.setId(1L);
        actorBO.setName("prueba");
        actorBO.setAge(25);
        actorBO.setNationality("Spain");
        List<ActorBO> actorsBO = new ArrayList<>();
        actorsBO.add(actorBO);

        directorBO = new DirectorBO();
        directorBO.setId(2L);
        directorBO.setName("prueba");
        directorBO.setAge(25);
        directorBO.setNationality("Spain");

        producerBO = new ProducerBO();
        producerBO.setId(1L);
        producerBO.setName("prueba");
        producerBO.setDebut(2020);

        serieBO = new SerieBO();
        serieBO.setId(1L);
        serieBO.setTitle("prueba");
        serieBO.setDebut(2020);
        serieBO.setActors(actorsBO);
        serieBO.setProducer(producerBO);
        serieBO.setDirector(directorBO);

        filmBO = new FilmBO();
        filmBO.setId(1L);
        filmBO.setTitle("prueba");
        filmBO.setDebut(2020);
        filmBO.setActors(actorsBO);
        filmBO.setProducer(producerBO);
        filmBO.setDirector(directorBO);

        actorDtoIn = new ActorDtoIn();
        actorDtoIn.setId(1L);
        actorDtoIn.setName("prueba");
        actorDtoIn.setAge(25);
        actorDtoIn.setNationality("Spain");
        List<ActorDtoIn> actorsDtoIn = new ArrayList<>();
        actorsDtoIn.add(actorDtoIn);

        directorDtoIn = new DirectorDtoIn();
        directorDtoIn.setId(2L);
        directorDtoIn.setName("prueba");
        directorDtoIn.setAge(25);
        directorDtoIn.setNationality("Spain");

        producerDtoIn = new ProducerDtoIn();
        producerDtoIn.setId(1L);
        producerDtoIn.setName("prueba");
        producerDtoIn.setDebut(2020);

        serieDtoIn = new SerieDtoIn();
        serieDtoIn.setId(1L);
        serieDtoIn.setTitle("prueba");
        serieDtoIn.setDebut(2020);
        serieDtoIn.setActors(actorsDtoIn);
        serieDtoIn.setProducer(producerDtoIn);
        serieDtoIn.setDirector(directorDtoIn);

        filmDtoIn = new FilmDtoIn();
        filmDtoIn.setId(1L);
        filmDtoIn.setTitle("prueba");
        filmDtoIn.setDebut(2020);
        filmDtoIn.setActors(actorsDtoIn);
        filmDtoIn.setProducer(producerDtoIn);
        filmDtoIn.setDirector(directorDtoIn);
    }

    @DisplayName("JUnit test for convert ActorDtoIn To ActorBO")
    @Test
    void givenActorDtoInObject_whenActorBoToDto_thenReturnActorBoObject() {

        ActorBO savedActorBO = DtoToBoConverter.actorDtoToBo(actorDtoIn);

        assertEquals(actorBO, savedActorBO);
    }

    @DisplayName("JUnit test for convert DirectorDtoIn To DirectorBO")
    @Test
    void givenDirectorDtoInObject_whenDirectorBoToDto_thenReturnDirectorBoObject() {

        DirectorBO savedDirectorBO = DtoToBoConverter.directorDtoToBo(directorDtoIn);

        assertEquals(directorBO, savedDirectorBO);
    }

    @DisplayName("JUnit test for convert ProducerDtoIn To DirectorBO")
    @Test
    void givenProducerDtoInObject_whenProducerBoToDto_thenReturnProducerBoObject() {

        ProducerBO savedProducerBO = DtoToBoConverter.producerDtoToBo(producerDtoIn);

        assertEquals(producerBO, savedProducerBO);
    }

    @DisplayName("JUnit test for convert SerieDtoIn To SerieBO")
    @Test
    void givenSerieDtoInObject_whenSerieBoToDto_thenReturnSerieBoObject() {

        SerieBO savedSerieBO = DtoToBoConverter.serieDtoToBo(serieDtoIn);

        assertEquals(serieBO, savedSerieBO);
    }

    @DisplayName("JUnit test for convert FilmDtoIn To FilmBO")
    @Test
    void givenFilmDtoInObject_whenFilmBoToDto_thenReturnFilmBoObject() {

        FilmBO savedFilmBO = DtoToBoConverter.filmDtoToBo(filmDtoIn);

        assertEquals(filmBO, savedFilmBO);
    }
}
