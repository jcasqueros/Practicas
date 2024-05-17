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

    private ActorDtoInUpdate actorDtoInUpdate;

    private DirectorBO directorBO;

    private DirectorDtoIn directorDtoIn;

    private DirectorDtoInUpdate directorDtoInUpdate;

    private ProducerBO producerBO;

    private ProducerDtoIn producerDtoIn;

    private ProducerDtoInUpdate producerDtoInUpdate;

    private SerieBO serieBO;

    private SerieDtoIn serieDtoIn;

    private SerieDtoInUpdate serieDtoInUpdate;

    private FilmBO filmBO;

    private FilmDtoIn filmDtoIn;

    private FilmDtoInUpdate filmDtoInUpdate;

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
        actorDtoIn.setName("prueba");
        actorDtoIn.setAge(25);
        actorDtoIn.setNationality("Spain");
        List<ActorDtoIn> actorsDtoIn = new ArrayList<>();
        actorsDtoIn.add(actorDtoIn);

        actorDtoInUpdate = new ActorDtoInUpdate();
        actorDtoInUpdate.setId(1L);
        actorDtoInUpdate.setName("prueba");
        actorDtoInUpdate.setAge(25);
        actorDtoInUpdate.setNationality("Spain");

        directorDtoIn = new DirectorDtoIn();
        directorDtoIn.setName("prueba");
        directorDtoIn.setAge(25);
        directorDtoIn.setNationality("Spain");

        directorDtoInUpdate = new DirectorDtoInUpdate();
        directorDtoInUpdate.setId(2L);
        directorDtoInUpdate.setName("prueba");
        directorDtoInUpdate.setAge(25);
        directorDtoInUpdate.setNationality("Spain");

        producerDtoIn = new ProducerDtoIn();
        producerDtoIn.setName("prueba");
        producerDtoIn.setDebut(2020);

        producerDtoInUpdate = new ProducerDtoInUpdate();
        producerDtoInUpdate.setId(1L);
        producerDtoInUpdate.setName("prueba");
        producerDtoInUpdate.setDebut(2020);

        serieDtoIn = new SerieDtoIn();
        serieDtoIn.setTitle("prueba");
        serieDtoIn.setDebut(2020);
        serieDtoIn.setActors(null);
        serieDtoIn.setProducer(null);
        serieDtoIn.setDirector(null);

        serieDtoInUpdate = new SerieDtoInUpdate();
        serieDtoInUpdate.setId(1L);
        serieDtoInUpdate.setTitle("prueba");
        serieDtoInUpdate.setDebut(2020);
        serieDtoInUpdate.setActors(null);
        serieDtoInUpdate.setProducer(null);
        serieDtoInUpdate.setDirector(null);

        filmDtoIn = new FilmDtoIn();
        filmDtoIn.setTitle("prueba");
        filmDtoIn.setDebut(2020);
        filmDtoIn.setActors(null);
        filmDtoIn.setProducer(null);
        filmDtoIn.setDirector(null);

        filmDtoInUpdate = new FilmDtoInUpdate();
        filmDtoInUpdate.setId(1L);
        filmDtoInUpdate.setTitle("prueba");
        filmDtoInUpdate.setDebut(2020);
        filmDtoInUpdate.setActors(null);
        filmDtoInUpdate.setProducer(null);
        filmDtoInUpdate.setDirector(null);
    }

    @DisplayName("JUnit test for convert ActorDtoIn To ActorBO")
    @Test
    void givenActorDtoInObject_whenActorBoToDto_thenReturnActorBoObject() {

        ActorBO savedActorBO = DtoToBoConverter.actorDtoToBo(actorDtoIn);

        assertEquals(actorBO.getName(), savedActorBO.getName());
    }

    @DisplayName("JUnit test for convert DirectorDtoIn To DirectorBO")
    @Test
    void givenDirectorDtoInObject_whenDirectorBoToDto_thenReturnDirectorBoObject() {

        DirectorBO savedDirectorBO = DtoToBoConverter.directorDtoToBo(directorDtoIn);

        assertEquals(directorBO.getName(), savedDirectorBO.getName());
    }

    @DisplayName("JUnit test for convert ProducerDtoIn To DirectorBO")
    @Test
    void givenProducerDtoInObject_whenProducerBoToDto_thenReturnProducerBoObject() {

        ProducerBO savedProducerBO = DtoToBoConverter.producerDtoToBo(producerDtoIn);

        assertEquals(producerBO.getName(), savedProducerBO.getName());
    }

    @DisplayName("JUnit test for convert SerieDtoIn To SerieBO")
    @Test
    void givenSerieDtoInObject_whenSerieBoToDto_thenReturnSerieBoObject() {

        SerieBO savedSerieBO = DtoToBoConverter.serieDtoToBo(serieDtoIn);

        assertEquals(serieBO.getTitle(), savedSerieBO.getTitle());
    }

    @DisplayName("JUnit test for convert FilmDtoIn To FilmBO")
    @Test
    void givenFilmDtoInObject_whenFilmBoToDto_thenReturnFilmBoObject() {

        FilmBO savedFilmBO = DtoToBoConverter.filmDtoToBo(filmDtoIn);

        assertEquals(filmBO.getTitle(), savedFilmBO.getTitle());
    }

    @DisplayName("JUnit test for convert ActorDtoInUpdate To ActorBO")
    @Test
    void givenActorDtoInUpdateObject_whenActorBoToDto_thenReturnActorBoObject() {

        ActorBO savedActorBO = DtoToBoConverter.actorDtoUpdateToBo(actorDtoInUpdate);

        assertEquals(actorBO, savedActorBO);
    }

    @DisplayName("JUnit test for convert DirectorDtoInUpdate To DirectorBO")
    @Test
    void givenDirectorDtoInUpdateObject_whenDirectorBoToDto_thenReturnDirectorBoObject() {

        DirectorBO savedDirectorBO = DtoToBoConverter.directorUpdateDtoToBo(directorDtoInUpdate);

        assertEquals(directorBO, savedDirectorBO);
    }

    @DisplayName("JUnit test for convert ProducerDtoInUpdate To DirectorBO")
    @Test
    void givenProducerDtoInUpdateObject_whenProducerBoToDto_thenReturnProducerBoObject() {

        ProducerBO savedProducerBO = DtoToBoConverter.producerDtoUpdateToBo(producerDtoInUpdate);

        assertEquals(producerBO, savedProducerBO);
    }

    @DisplayName("JUnit test for convert SerieDtoInUpdate To SerieBO")
    @Test
    void givenSerieDtoInUpdateObject_whenSerieBoToDto_thenReturnSerieBoObject() {

        SerieBO savedSerieBO = DtoToBoConverter.serieDtoUpdateToBo(serieDtoInUpdate);

        assertEquals(serieBO.getTitle(), savedSerieBO.getTitle());
    }

    @DisplayName("JUnit test for convert FilmDtoInUpdate To FilmBO")
    @Test
    void givenFilmDtoInUpdateObject_whenFilmBoToDto_thenReturnFilmBoObject() {

        FilmBO savedFilmBO = DtoToBoConverter.filmDtoUpdateToBo(filmDtoInUpdate);

        assertEquals(filmBO.getTitle(), savedFilmBO.getTitle());
    }
}
