package com.pracs.films.bussiness.converters;

import com.pracs.films.bussiness.bo.*;
import com.pracs.films.persistence.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ModelToBoConverterTest {

    @Autowired
    private ModelToBoConverter modelToBoConverter;

    private Actor actor;

    private ActorBO actorBO;

    private Director director;

    private DirectorBO directorBO;

    private Producer producer;

    private ProducerBO producerBO;

    private Serie serie;

    private SerieBO serieBO;

    private Film film;

    private FilmBO filmBO;

    @BeforeEach
    void setup() {

        actor = new Actor();
        actor.setId(1L);
        actor.setName("prueba");
        actor.setAge(25);
        actor.setNationality("Spain");
        List<Actor> actors = new ArrayList<>();
        actors.add(actor);

        director = new Director();
        director.setId(2L);
        director.setName("prueba");
        director.setAge(25);
        director.setNationality("Spain");

        producer = new Producer();
        producer.setId(1L);
        producer.setName("prueba");
        producer.setDebut(2020);

        serie = new Serie();
        serie.setId(1L);
        serie.setTitle("prueba");
        serie.setDebut(2020);
        serie.setActors(actors);
        serie.setProducer(producer);
        serie.setDirector(director);

        film = new Film();
        film.setId(1L);
        film.setTitle("prueba");
        film.setDebut(2020);
        film.setActors(actors);
        film.setProducer(producer);
        film.setDirector(director);

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
    }

    @DisplayName("JUnit test for convert Actor Model To ActorBO")
    @Test
    void givenActorModelObject_whenActorModelToBo_thenReturnActorBoObject() {

        ActorBO savedActorBO = modelToBoConverter.actorModelToBo(actor);

        assertEquals(actorBO, savedActorBO);
    }

    @DisplayName("JUnit test for convert Director Model To DirectorBO")
    @Test
    void givenDirectorModelObject_whenDirectorModelToBo_thenReturnDirectorBoObject() {

        DirectorBO savedDirectorBO = modelToBoConverter.directorModelToBo(director);

        assertEquals(directorBO, savedDirectorBO);
    }

    @DisplayName("JUnit test for convert Producer Model To ProducerBO")
    @Test
    void givenProducerModelObject_whenProducerModelToBo_thenReturnProducerBoObject() {

        ProducerBO savedProducerBO = modelToBoConverter.producerModelToBo(producer);

        assertEquals(producerBO, savedProducerBO);
    }

    @DisplayName("JUnit test for convert Serie Model To SerieBO")
    @Test
    void givenSerieModelObject_whenSerieModelToBo_thenReturnSerieBoObject() {

        SerieBO savedSerieBO = modelToBoConverter.serieModelToBo(serie);

        assertEquals(serieBO, savedSerieBO);
    }

    @DisplayName("JUnit test for convert Film Model To FilmBO")
    @Test
    void givenFilmModelObject_whenFilmModelToBo_thenReturnFilmBoObject() {

        FilmBO savedFilmBO = modelToBoConverter.filmModelToBo(film);

        assertEquals(filmBO, savedFilmBO);
    }
}
