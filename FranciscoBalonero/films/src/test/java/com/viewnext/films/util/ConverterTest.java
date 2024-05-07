package com.viewnext.films.util;

import com.viewnext.films.businesslayer.bo.*;
import com.viewnext.films.persistencelayer.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ConverterTest {
    private Actor actor;
    private ActorBO actorBO;
    private Director director;
    private DirectorBO directorBO;
    private Film film;
    private FilmBO filmBO;
    private Producer producer;
    private ProducerBO producerBO;
    private Serie serie;
    private SerieBO serieBO;

    @Autowired
    private Converter converter;

    @BeforeEach
    public void setup() {

        actor = new Actor();
        actor.setId(1L);
        actor.setAge(18);
        actor.setName("Jhon");
        actor.setNationality("spain");

        actorBO = new ActorBO();
        actorBO.setId(1L);
        actorBO.setAge(18);
        actorBO.setName("Jhon");
        actorBO.setNationality("spain");

        director = new Director();
        director.setId(1L);
        director.setAge(18);
        director.setName("Jhon");
        director.setNationality("spain");

        directorBO = new DirectorBO();
        directorBO.setId(1L);
        directorBO.setAge(18);
        directorBO.setName("Jhon");
        directorBO.setNationality("spain");

        producer = new Producer();
        producer.setId(1L);
        producer.setName("Paramount");
        producer.setFoundationYear(2003);

        producerBO = new ProducerBO();
        producerBO.setId(1L);
        producerBO.setName("Paramount");
        producerBO.setFoundationYear(2003);

        film = new Film();
        film.setId(1L);
        film.setTitle("Friends");
        film.setReleaseYear(2004);
        film.setDirector(director);
        film.setProducer(producer);
        List<Actor> actors = new ArrayList<>();
        actors.add(actor);
        film.setActors(actors);

        filmBO = new FilmBO();
        filmBO.setId(1L);
        filmBO.setTitle("Friends");
        filmBO.setReleaseYear(2004);
        filmBO.setDirector(directorBO);
        filmBO.setProducer(producerBO);
        List<ActorBO> actorsBO = new ArrayList<>();
        actorsBO.add(actorBO);
        filmBO.setActors(actorsBO);

        serie = new Serie();
        serie.setId(1L);
        serie.setTitle("Friends");
        serie.setReleaseYear(2004);
        serie.setDirector(director);
        serie.setProducer(producer);
        actors.add(actor);
        serie.setActors(actors);

        serieBO = new SerieBO();
        serieBO.setId(1L);
        serieBO.setTitle("Friends");
        serieBO.setReleaseYear(2004);
        serieBO.setDirector(directorBO);
        serieBO.setProducer(producerBO);
        actorsBO.add(actorBO);
        serieBO.setActors(actorsBO);

    }

    @Test
    @DisplayName("ActorBO -> ActorEntity")
    void givenActorBO_whenActorBOToEntity_thenReturnActorEntity() {
        Actor test = converter.actorBOToEntity(actorBO);
        assertEquals(actor, test);
    }

    @Test
    @DisplayName("ActorEntity -> ActorBO")
    void givenActorEntity_whenActorEntityToBO_thenReturnActorBO() {
        ActorBO test = converter.actorEntityToBO(actor);
        assertEquals(actorBO, test);
    }

    @Test
    @DisplayName("DirectorBO -> DirectorEntity")
    void givenDirectorBO_whenDirectorBOToEntity_thenReturnDirectorEntity() {
        Director test = converter.directorBOToEntity(directorBO);
        assertEquals(director, test);
    }

    @Test
    @DisplayName("DirectorEntity -> DirectorBO")
    void givenDirectorEntity_whenDirectorEntityToBO_thenReturnDirectorBO() {
        DirectorBO test = converter.directorEntityToBO(director);
        assertEquals(directorBO, test);
    }

    @Test
    @DisplayName("FilmBO -> FilmEntity")
    void givenFilmBO_whenFilmBOToEntity_thenReturnFilmEntity() {
        Film test = converter.filmBOToEntity(filmBO);
        assertEquals(film, test);
    }

    @Test
    @DisplayName("FilmEntity -> FilmBO")
    void givenFilmEntity_whenFilmEntityToBO_thenReturnFilmBO() {
        FilmBO test = converter.filmEntityToBO(film);
        assertEquals(filmBO, test);
    }

    @Test
    @DisplayName("ProducerBO -> ProducerEntity")
    void givenProducerBO_whenProducerBOToEntity_thenReturnProducerEntity() {
        Producer test = converter.producerBOToEntity(producerBO);
        assertEquals(producer, test);
    }

    @Test
    @DisplayName("ProducerEntity -> ProducerBO")
    void givenProducerEntity_whenProducerEntityToBO_thenReturnProducerBO() {
        ProducerBO test = converter.producerEntityToBO(producer);
        assertEquals(producerBO, test);
    }

    @Test
    @DisplayName("SerieBO -> SerieEntity")
    void givenSerieBO_whenSerieBOToEntity_thenReturnSerieEntity() {
        Serie test = converter.serieBOToEntity(serieBO);
        assertEquals(serie, test);
    }

    @Test
    @DisplayName("SerieEntity -> SerieBO")
    void givenSerieEntity_whenSerieEntityToBO_thenReturnSerieBO() {
        SerieBO test = converter.serieEntityToBO(serie);
        assertEquals(serieBO, test);
    }

    @Test
    @DisplayName("ActorBO -> ActorEntity: null input")
    void givenNullActorBO_whenActorBOToEntity_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.actorBOToEntity(null));
    }

    @Test
    @DisplayName("ActorEntity -> ActorBO: null input")
    void givenNullActorEntity_whenActorEntityToBO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.actorEntityToBO(null));
    }

    @Test
    @DisplayName("DirectorBO -> DirectorEntity: null input")
    void givenNullDirectorBO_whenDirectorBOToEntity_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.directorBOToEntity(null));
    }

    @Test
    @DisplayName("DirectorEntity -> DirectorBO: null input")
    void givenNullDirectorEntity_whenDirectorEntityToBO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.directorEntityToBO(null));
    }

    @Test
    @DisplayName("FilmBO -> FilmEntity: null input")
    void givenNullFilmBO_whenFilmBOToEntity_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.filmBOToEntity(null));
    }

    @Test
    @DisplayName("FilmEntity -> FilmBO: null input")
    void givenNullFilmEntity_whenFilmEntityToBO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.filmEntityToBO(null));
    }

    @Test
    @DisplayName("ProducerBO -> ProducerEntity: null input")
    void givenNullProducerBO_whenProducerBOToEntity_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.producerBOToEntity(null));
    }

    @Test
    @DisplayName("ProducerEntity -> ProducerBO: null input")
    void givenNullProducerEntity_whenProducerEntityToBO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.producerEntityToBO(null));
    }

    @Test
    @DisplayName("SerieBO -> SerieEntity: null input")
    void givenNullSerieBO_whenSerieBOToEntity_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.serieBOToEntity(null));
    }

    @Test
    @DisplayName("SerieEntity -> SerieBO: null input")
    void givenNullSerieEntity_whenSerieEntityToBO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.serieEntityToBO(null));
    }
}

