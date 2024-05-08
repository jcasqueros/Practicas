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
public class BoToDtoConverterTest {

    @Autowired
    private BoToDtoConverter boToDtoConverter;

    private ActorBO actorBO;

    private ActorDtoOut actorDtoOut;

    private DirectorBO directorBO;

    private DirectorDtoOut directorDtoOut;

    private ProducerBO producerBO;

    private ProducerDtoOut producerDtoOut;

    private SerieBO serieBO;

    private SerieDtoOut serieDtoOut;

    private FilmBO filmBO;

    private FilmDtoOut filmDtoOut;

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

        actorDtoOut = new ActorDtoOut();
        actorDtoOut.setId(1L);
        actorDtoOut.setName("prueba");
        actorDtoOut.setAge(25);
        actorDtoOut.setNationality("Spain");
        List<ActorDtoOut> actorsDtoOut = new ArrayList<>();
        actorsDtoOut.add(actorDtoOut);

        directorDtoOut = new DirectorDtoOut();
        directorDtoOut.setId(2L);
        directorDtoOut.setName("prueba");
        directorDtoOut.setAge(25);
        directorDtoOut.setNationality("Spain");

        producerDtoOut = new ProducerDtoOut();
        producerDtoOut.setId(1L);
        producerDtoOut.setName("prueba");
        producerDtoOut.setDebut(2020);

        serieDtoOut = new SerieDtoOut();
        serieDtoOut.setId(1L);
        serieDtoOut.setTitle("prueba");
        serieDtoOut.setDebut(2020);
        serieDtoOut.setActors(actorsDtoOut);
        serieDtoOut.setProducer(producerDtoOut);
        serieDtoOut.setDirector(directorDtoOut);

        filmDtoOut = new FilmDtoOut();
        filmDtoOut.setId(1L);
        filmDtoOut.setTitle("prueba");
        filmDtoOut.setDebut(2020);
        filmDtoOut.setActors(actorsDtoOut);
        filmDtoOut.setProducer(producerDtoOut);
        filmDtoOut.setDirector(directorDtoOut);
    }

    @DisplayName("JUnit test for convert ActorBO To ActorDtoOut")
    @Test
    void givenActorBoObject_whenActorBoToDto_thenReturnActorDtoOutObject() {

        ActorDtoOut savedActorDto = boToDtoConverter.actorBoToDtoOut(actorBO);

        assertEquals(actorDtoOut, savedActorDto);
    }

    @DisplayName("JUnit test for convert DirectorBO To DirectorDtoOut")
    @Test
    void givenDirectorBoObject_whenDirectorBoToDto_thenReturnDirectorDtoOutObject() {

        DirectorDtoOut savedDirectorDto = boToDtoConverter.directorBoToDtoOut(directorBO);

        assertEquals(directorDtoOut, savedDirectorDto);
    }

    @DisplayName("JUnit test for convert ProducerBO To ProducerDtoOut")
    @Test
    void givenProducerBoObject_whenProducerBoToDto_thenReturnProducerDtoOutObject() {

        ProducerDtoOut savedProducerDto = boToDtoConverter.producerBoToDtoOut(producerBO);

        assertEquals(producerDtoOut, savedProducerDto);
    }

    @DisplayName("JUnit test for convert SerieBO To SerieDtoOut")
    @Test
    void givenSerieBoObject_whenSerieBoToDto_thenReturnSerieDtoOutObject() {

        SerieDtoOut savedSerieDto = boToDtoConverter.serieBoToDtoOut(serieBO);

        assertEquals(serieDtoOut, savedSerieDto);
    }

    @DisplayName("JUnit test for convert FilmBO To FilmDtoOut")
    @Test
    void givenFilmBoObject_whenFilmBoToDto_thenReturnFilmDtoOutObject() {

        FilmDtoOut savedFilmDto = boToDtoConverter.filmBoToDtoOut(filmBO);

        assertEquals(filmDtoOut, savedFilmDto);
    }
}
