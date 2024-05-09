package com.viewnext.films.util;

import com.viewnext.films.businesslayer.bo.*;
import com.viewnext.films.persistencelayer.entity.*;
import com.viewnext.films.presentationlayer.dto.*;
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
    private ActorInDTO actorInDTO;
    private ActorOutDTO actorOutDTO;
    private ActorUpdateDTO actorUpdateDTO;
    private DirectorInDTO directorInDTO;
    private DirectorOutDTO directorOutDTO;
    private DirectorUpdateDTO directorUpdateDTO;
    private ProducerInDTO producerInDTO;
    private ProducerOutDTO producerOutDTO;
    private ProducerUpdateDTO producerUpdateDTO;
    private FilmInDTO filmInDTO;
    private FilmOutDTO filmOutDTO;
    private FilmUpdateDTO filmUpdateDTO;
    private SerieInDTO serieInDTO;
    private SerieOutDTO serieOutDTO;
    private SerieUpdateDTO serieUpdateDTO;
    @Autowired
    private Converter converter;

    @BeforeEach
    public void setup() {

        actor = new Actor();
        actor.setId(1L);
        actor.setAge(18);
        actor.setName("Jhon");
        actor.setNationality("Spain");

        actorBO = new ActorBO();
        actorBO.setId(1L);
        actorBO.setAge(18);
        actorBO.setName("Jhon");
        actorBO.setNationality("Spain");

        director = new Director();
        director.setId(1L);
        director.setAge(18);
        director.setName("Jhon");
        director.setNationality("Spain");

        directorBO = new DirectorBO();
        directorBO.setId(1L);
        directorBO.setAge(18);
        directorBO.setName("Jhon");
        directorBO.setNationality("Spain");

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
        serie.setActors(actors);

        serieBO = new SerieBO();
        serieBO.setId(1L);
        serieBO.setTitle("Friends");
        serieBO.setReleaseYear(2004);
        serieBO.setDirector(directorBO);
        serieBO.setProducer(producerBO);
        serieBO.setActors(actorsBO);

        actorInDTO = new ActorInDTO();
        actorInDTO.setName("Jhon");
        actorInDTO.setAge(18);
        actorInDTO.setNationality("Spain");

        actorOutDTO = new ActorOutDTO();
        actorOutDTO.setId(1L);
        actorOutDTO.setName("Jhon");
        actorOutDTO.setAge(18);
        actorOutDTO.setNationality("Spain");

        actorUpdateDTO = new ActorUpdateDTO();
        actorUpdateDTO.setId(1L);
        actorUpdateDTO.setName("Jhon");
        actorUpdateDTO.setAge(18);
        actorUpdateDTO.setNationality("Spain");

        directorInDTO = new DirectorInDTO();
        directorInDTO.setName("Jhon");
        directorInDTO.setAge(18);
        directorInDTO.setNationality("Spain");

        directorOutDTO = new DirectorOutDTO();
        directorOutDTO.setId(1L);
        directorOutDTO.setName("Jhon");
        directorOutDTO.setAge(18);
        directorOutDTO.setNationality("Spain");

        directorUpdateDTO = new DirectorUpdateDTO();
        directorUpdateDTO.setId(1L);
        directorUpdateDTO.setName("Jhon");
        directorUpdateDTO.setAge(18);
        directorUpdateDTO.setNationality("Spain");

        producerInDTO = new ProducerInDTO();
        producerInDTO.setName("Paramount");
        producerInDTO.setFoundationYear(2003);

        producerOutDTO = new ProducerOutDTO();
        producerOutDTO.setId(1L);
        producerOutDTO.setName("Paramount");
        producerOutDTO.setFoundationYear(2003);

        producerUpdateDTO = new ProducerUpdateDTO();
        producerUpdateDTO.setId(1L);
        producerUpdateDTO.setName("Paramount");
        producerUpdateDTO.setFoundationYear(2003);

        filmInDTO = new FilmInDTO();
        filmInDTO.setTitle("Friends");
        filmInDTO.setReleaseYear(2004);
        filmInDTO.setDirector(directorOutDTO);
        filmInDTO.setProducer(producerOutDTO);
        List<ActorOutDTO> actorsOutDTO = new ArrayList<>();
        actorsOutDTO.add(actorOutDTO);
        filmInDTO.setActors(actorsOutDTO);

        filmOutDTO = new FilmOutDTO();
        filmOutDTO.setId(1L);
        filmOutDTO.setTitle("Friends");
        filmOutDTO.setReleaseYear(2004);
        filmOutDTO.setDirector(directorOutDTO);
        filmOutDTO.setProducer(producerOutDTO);
        filmOutDTO.setActors(actorsOutDTO);

        filmUpdateDTO = new FilmUpdateDTO();
        filmUpdateDTO.setId(1L);
        filmUpdateDTO.setTitle("Friends");
        filmUpdateDTO.setReleaseYear(2004);
        filmUpdateDTO.setDirector(directorOutDTO);
        filmUpdateDTO.setProducer(producerOutDTO);
        filmUpdateDTO.setActors(actorsOutDTO);

        serieInDTO = new SerieInDTO();
        serieInDTO.setTitle("Friends");
        serieInDTO.setReleaseYear(2004);
        serieInDTO.setDirector(directorOutDTO);
        serieInDTO.setProducer(producerOutDTO);
        serieInDTO.setActors(actorsOutDTO);

        serieOutDTO = new SerieOutDTO();
        serieOutDTO.setId(1L);
        serieOutDTO.setTitle("Friends");
        serieOutDTO.setReleaseYear(2004);
        serieOutDTO.setDirector(directorOutDTO);
        serieOutDTO.setProducer(producerOutDTO);
        serieOutDTO.setActors(actorsOutDTO);

        serieUpdateDTO = new SerieUpdateDTO();
        serieUpdateDTO.setId(1L);
        serieUpdateDTO.setTitle("Friends");
        serieUpdateDTO.setReleaseYear(2004);
        serieUpdateDTO.setDirector(directorOutDTO);
        serieUpdateDTO.setProducer(producerOutDTO);
        serieUpdateDTO.setActors(actorsOutDTO);
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

    @Test
    @DisplayName("ActorBO -> ActorOutDTO")
    void givenActorBO_whenActorBOToOutDTO_thenReturnActorOutDTO() {
        ActorOutDTO test = converter.actorBOToOutDTO(actorBO);
        assertEquals(actorOutDTO.getName(), test.getName());
        assertEquals(actorOutDTO.getAge(), test.getAge());
        assertEquals(actorOutDTO.getNationality(), test.getNationality());
    }

    @Test
    @DisplayName("ActorInDTO -> ActorBO")
    void givenActorInDTO_whenActorInDTOToBO_thenReturnActorBO() {
        ActorBO test = converter.actorInDTOToBO(actorInDTO);
        assertEquals(actorBO.getName(), test.getName());
        assertEquals(actorBO.getNationality(), test.getNationality());
        assertEquals(actorBO.getAge(), test.getAge());
    }

    @Test
    @DisplayName("ActorUpdateDTO -> ActorBO")
    void givenActorUpdateDTO_whenActorUpdateDTOToBO_thenReturnActorBO() {
        ActorBO test = converter.actorUpdateDTOToBO(actorUpdateDTO);
        assertEquals(actorBO, test);
    }

    @Test
    @DisplayName("DirectorBO -> DirectorOutDTO")
    void givenDirectorBO_whenDirectorBOToOutDTO_thenReturnDirectorOutDTO() {
        DirectorOutDTO test = converter.directorBOToOutDTO(directorBO);
        assertEquals(directorOutDTO, test);
    }

    @Test
    @DisplayName("DirectorInDTO -> DirectorBO")
    void givenDirectorInDTO_whenDirectorInDTOToBO_thenReturnDirectorBO() {
        DirectorBO test = converter.directorInDTOToBO(directorInDTO);
        assertEquals(directorBO.getName(), test.getName());
        assertEquals(directorBO.getNationality(), test.getNationality());
        assertEquals(directorBO.getAge(), test.getAge());
    }

    @Test
    @DisplayName("DirectorUpdateDTO -> DirectorBO")
    void givenDirectorUpdateDTO_whenDirectorUpdateDTOToBO_thenReturnDirectorBO() {
        DirectorBO test = converter.directorUpdateDTOToBO(directorUpdateDTO);
        assertEquals(directorBO, test);
    }

    @Test
    @DisplayName("FilmBO -> FilmOutDTO")
    void givenFilmBO_whenFilmBOToOutDTO_thenReturnFilmOutDTO() {
        FilmOutDTO test = converter.filmBOToOutDTO(filmBO);
        assertEquals(filmOutDTO, test);
    }

    @Test
    @DisplayName("FilmInDTO -> FilmBO")
    void givenFilmInDTO_whenFilmInDTOToBO_thenReturnFilmBO() {
        FilmBO test = converter.filmInDTOToBO(filmInDTO);
        assertEquals(filmBO.getTitle(), test.getTitle());
        assertEquals(filmBO.getActors(), test.getActors());
        assertEquals(filmBO.getProducer(), test.getProducer());
        assertEquals(filmBO.getDirector(), test.getDirector());
        assertEquals(filmBO.getReleaseYear(), test.getReleaseYear());
    }

    @Test
    @DisplayName("FilmUpdateDTO -> FilmBO")
    void givenFilmUpdateDTO_whenFilmUpdateDTOToBO_thenReturnFilmBO() {
        FilmBO test = converter.filmUpdateDTOToBO(filmUpdateDTO);
        assertEquals(filmBO, test);
    }

    @Test
    @DisplayName("SerieBO -> SerieOutDTO")
    void givenSerieBO_whenSerieBOToOutDTO_thenReturnSerieOutDTO() {
        SerieOutDTO test = converter.serieBOToOutDTO(serieBO);
        assertEquals(serieOutDTO, test);
    }

    @Test
    @DisplayName("SerieInDTO -> SerieBO")
    void givenSerieInDTO_whenSerieInDTOToBO_thenReturnSerieBO() {
        SerieBO test = converter.serieInDTOToBO(serieInDTO);
        assertEquals(serieBO.getTitle(), test.getTitle());
        assertEquals(serieBO.getActors(), test.getActors());
        assertEquals(serieBO.getProducer(), test.getProducer());
        assertEquals(serieBO.getDirector(), test.getDirector());
        assertEquals(serieBO.getReleaseYear(), test.getReleaseYear());
    }

    @Test
    @DisplayName("SerieUpdateDTO -> SerieBO")
    void givenSerieUpdateDTO_whenSerieUpdateDTOToBO_thenReturnSerieBO() {
        SerieBO test = converter.serieUpdateDTOToBO(serieUpdateDTO);
        assertEquals(serieBO, test);
    }

    @Test
    @DisplayName("ProducerBO -> ProducerOutDTO")
    void givenProducerBO_whenProducerBOToOutDTO_thenReturnProducerOutDTO() {
        ProducerOutDTO test = converter.producerBOToOutDTO(producerBO);
        assertEquals(producerOutDTO, test);
    }

    @Test
    @DisplayName("ProducerInDTO -> ProducerBO")
    void givenProducerInDTO_whenProducerInDTOToBO_thenReturnProducerBO() {
        ProducerBO test = converter.producerInDTOToBO(producerInDTO);
        assertEquals(producerBO.getName(), test.getName());
        assertEquals(producerBO.getFoundationYear(), test.getFoundationYear());
    }

    @Test
    @DisplayName("ProducerUpdateDTO -> ProducerBO")
    void givenProducerUpdateDTO_whenProducerUpdateDTOToBO_thenReturnProducerBO() {
        ProducerBO test = converter.producerUpdateDTOToBO(producerUpdateDTO);
        assertEquals(producerBO, test);
    }

    // Tests for null inputs
    @Test
    @DisplayName("ActorBO -> ActorOutDTO: null input")
    void givenNullActorBO_whenActorBOToOutDTO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.actorBOToOutDTO(null));
    }

    @Test
    @DisplayName("ActorInDTO -> ActorBO: null input")
    void givenNullActorInDTO_whenActorInDTOToBO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.actorInDTOToBO(null));
    }

    @Test
    @DisplayName("ActorUpdateDTO -> ActorBO: null input")
    void givenNullActorUpdateDTO_whenActorUpdateDTOToBO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.actorUpdateDTOToBO(null));
    }

    @Test
    @DisplayName("DirectorBO -> DirectorOutDTO: null input")
    void givenNullDirectorBO_whenDirectorBOToOutDTO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.directorBOToOutDTO(null));
    }

    @Test
    @DisplayName("DirectorInDTO -> DirectorBO: null input")
    void givenNullDirectorInDTO_whenDirectorInDTOToBO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.directorInDTOToBO(null));
    }

    @Test
    @DisplayName("DirectorUpdateDTO -> DirectorBO: null input")
    void givenNullDirectorUpdateDTO_whenDirectorUpdateDTOToBO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.directorUpdateDTOToBO(null));
    }

    @Test
    @DisplayName("FilmBO -> FilmOutDTO: null input")
    void givenNullFilmBO_whenFilmBOToOutDTO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.filmBOToOutDTO(null));
    }

    @Test
    @DisplayName("FilmInDTO -> FilmBO: null input")
    void givenNullFilmInDTO_whenFilmInDTOToBO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.filmInDTOToBO(null));
    }

    @Test
    @DisplayName("FilmUpdateDTO -> FilmBO: null input")
    void givenNullFilmUpdateDTO_whenFilmUpdateDTOToBO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.filmUpdateDTOToBO(null));
    }

    @Test
    @DisplayName("SerieBO -> SerieOutDTO: null input")
    void givenNullSerieBO_whenSerieBOToOutDTO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.serieBOToOutDTO(null));
    }

    @Test
    @DisplayName("SerieInDTO -> SerieBO: null input")
    void givenNullSerieInDTO_whenSerieInDTOToBO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.serieInDTOToBO(null));
    }

    @Test
    @DisplayName("SerieUpdateDTO -> SerieBO: null input")
    void givenNullSerieUpdateDTO_whenSerieUpdateDTOToBO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.serieUpdateDTOToBO(null));
    }

    @Test
    @DisplayName("ProducerBO -> ProducerOutDTO: null input")
    void givenNullProducerBO_whenProducerBOToOutDTO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.producerBOToOutDTO(null));
    }

    @Test
    @DisplayName("ProducerInDTO -> ProducerBO: null input")
    void givenNullProducerInDTO_whenProducerInDTOToBO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.producerInDTOToBO(null));
    }

    @Test
    @DisplayName("ProducerUpdateDTO -> ProducerBO: null input")
    void givenNullProducerUpdateDTO_whenProducerUpdateDTOToBO_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> converter.producerUpdateDTOToBO(null));
    }

}

