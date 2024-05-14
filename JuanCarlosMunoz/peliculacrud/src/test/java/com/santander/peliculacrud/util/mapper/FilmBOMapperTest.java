package com.santander.peliculacrud.util.mapper;


import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.bo.FilmBO;
import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.model.entity.Director;
import com.santander.peliculacrud.model.entity.Film;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Film bo mapper test.
 */
@ExtendWith(MockitoExtension.class)
class FilmBOMapperTest {

    private FilmBOMapper filmBOMapper = Mappers.getMapper(FilmBOMapper.class);

    /**
     * Test bo to entity.
     */
    @Test
    @DisplayName("Test mapper filmBO to entity")
    void testBoToEntity() {
        FilmBO filmBO = FilmBO.builder()
                .title("Film 1")
                .created(2020)
                .actors(Arrays.asList(ActorBO.builder().name("Actor 1").build(), ActorBO.builder().name("Actor 2").build()))
                .director(DirectorBO.builder().name("Director 1").build())
                .build();

        Film film = filmBOMapper.boToEntity(filmBO);

        assertNotNull(film);
        assertEquals("Film 1", film.getTitle());
        assertEquals(2020, film.getCreated());
        assertNotNull(film.getActors());
        assertEquals(2, film.getActors().size());
        assertEquals("Actor 1", film.getActors().get(0).getName());
        assertEquals("Actor 2", film.getActors().get(1).getName());
        assertNotNull(film.getDirector());
        assertEquals("Director 1", film.getDirector().getName());
    }

    /**
     * Test entity to bo.
     */
    @Test
    @DisplayName("Test mapper entity to filmBO")
    void testEntityToBo() {
        Film film = Film.builder()
                .title("Film 1")
                .created(2020)
                .actors(Arrays.asList(Actor.builder().name("Actor 1").build(), Actor.builder().name("Actor 2").build()))
                .director(Director.builder().name("Director 1").build())
                .build();

        FilmBO filmBO = filmBOMapper.entityToBo(film);

        assertNotNull(filmBO);
        assertEquals("Film 1", filmBO.getTitle());
        assertEquals(2020, filmBO.getCreated());
        assertNotNull(filmBO.getActors());
        assertEquals(2, filmBO.getActors().size());
        assertEquals("Actor 1", filmBO.getActors().get(0).getName());
        assertEquals("Actor 2", filmBO.getActors().get(1).getName());
        assertNotNull(filmBO.getDirector());
        assertEquals("Director 1", filmBO.getDirector().getName());
    }
}
