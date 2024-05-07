package com.viewnext.films.util;

import com.viewnext.films.businesslayer.bo.*;
import com.viewnext.films.persistencelayer.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Converter class for mapping business objects, entities and data transfer objects.
 *
 * <p>This class uses the ModelMapper library for automatic mapping.</p>
 *
 * @author Francisco Balonero Olivera
 * @see ModelMapper
 */
@Component
@RequiredArgsConstructor
public class Converter {

    private final ModelMapper modelMapper;

    /**
     * Converts an {@link ActorBO} to an {@link Actor} entity.
     *
     * @param actorBO
     *         The {@link ActorBO}
     * @return The corresponding {@link Actor} entity
     */
    public Actor actorBOToEntity(ActorBO actorBO) {
        return modelMapper.map(actorBO, Actor.class);
    }

    /**
     * Converts an {@link Actor} entity to an {@link ActorBO}.
     *
     * @param actor
     *         The {@link Actor} entity
     * @return The corresponding {@link ActorBO}
     */
    public ActorBO actorEntityToBO(Actor actor) {
        return modelMapper.map(actor, ActorBO.class);
    }

    /**
     * Converts a {@link DirectorBO} to a {@link Director} entity.
     *
     * @param directorBO
     *         The {@link DirectorBO}
     * @return The corresponding {@link Director} entity
     */
    public Director directorBOToEntity(DirectorBO directorBO) {
        return modelMapper.map(directorBO, Director.class);
    }

    /**
     * Converts a {@link Director} entity to a {@link DirectorBO}.
     *
     * @param director
     *         The {@link Director} entity
     * @return The corresponding {@link DirectorBO}
     */
    public DirectorBO directorEntityToBO(Director director) {
        return modelMapper.map(director, DirectorBO.class);
    }

    /**
     * Converts a {@link FilmBO} to a {@link Film} entity.
     *
     * @param filmBO
     *         The {@link FilmBO}
     * @return The corresponding {@link Film} entity
     */
    public Film filmBOToEntity(FilmBO filmBO) {
        return modelMapper.map(filmBO, Film.class);
    }

    /**
     * Converts a {@link Film} entity to a {@link FilmBO}.
     *
     * @param film
     *         The {@link Film} entity
     * @return The corresponding {@link FilmBO}
     */
    public FilmBO filmEntityToBO(Film film) {
        return modelMapper.map(film, FilmBO.class);
    }

    /**
     * Converts a {@link ProducerBO} to a {@link Producer} entity.
     *
     * @param producerBO
     *         The {@link ProducerBO}
     * @return The corresponding {@link Producer} entity
     */
    public Producer producerBOToEntity(ProducerBO producerBO) {
        return modelMapper.map(producerBO, Producer.class);
    }

    /**
     * Converts a {@link Producer} entity to a {@link ProducerBO}.
     *
     * @param producer
     *         The {@link Producer} entity
     * @return The corresponding {@link ProducerBO}
     */
    public ProducerBO producerEntityToBO(Producer producer) {
        return modelMapper.map(producer, ProducerBO.class);
    }

    /**
     * Converts a {@link SerieBO} to a {@link Serie} entity.
     *
     * @param serieBO
     *         The {@link SerieBO}
     * @return The corresponding {@link Serie} entity
     */
    public Serie serieBOToEntity(SerieBO serieBO) {
        return modelMapper.map(serieBO, Serie.class);
    }

    /**
     * Converts a {@link Serie} entity to a {@link SerieBO}.
     *
     * @param serie
     *         The {@link Serie} entity
     * @return The corresponding {@link SerieBO}
     */
    public SerieBO serieEntityToBO(Serie serie) {
        return modelMapper.map(serie, SerieBO.class);
    }
}
