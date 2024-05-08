package com.viewnext.films.util;

import com.viewnext.films.businesslayer.bo.*;
import com.viewnext.films.persistencelayer.entity.*;
import com.viewnext.films.presentationlayer.dto.*;
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

    public ActorOutDTO actorBOToOutDTO(ActorBO actorBO) {
        return modelMapper.map(actorBO, ActorOutDTO.class);
    }

    public ActorBO actorInDTOToBO(ActorInDTO actorInDTO) {
        return modelMapper.map(actorInDTO, ActorBO.class);
    }

    public ActorBO actorUpdateDTOToBO(ActorUpdateDTO actorUpdateDTO) {
        return modelMapper.map(actorUpdateDTO, ActorBO.class);
    }

    public DirectorOutDTO directorBOToOutDTO(DirectorBO directorBO) {
        return modelMapper.map(directorBO, DirectorOutDTO.class);
    }

    public DirectorBO directorInDTOToBO(DirectorInDTO directorInDTO) {
        return modelMapper.map(directorInDTO, DirectorBO.class);
    }

    public DirectorBO directorUpdateDTOToBO(DirectorUpdateDTO directorUpdateDTO) {
        return modelMapper.map(directorUpdateDTO, DirectorBO.class);
    }

    public FilmOutDTO filmBOToOutDTO(FilmBO filmBO) {
        return modelMapper.map(filmBO, FilmOutDTO.class);
    }

    public FilmBO filmInDTOToBO(FilmInDTO filmInDTO) {
        return modelMapper.map(filmInDTO, FilmBO.class);
    }

    public FilmBO filmUpdateDTOToBO(FilmUpdateDTO filmUpdateDTO) {
        return modelMapper.map(filmUpdateDTO, FilmBO.class);
    }

    public SerieOutDTO serieBOToOutDTO(SerieBO serieBO) {
        return modelMapper.map(serieBO, SerieOutDTO.class);
    }

    public SerieBO serieInDTOToBO(SerieInDTO serieInDTO) {
        return modelMapper.map(serieInDTO, SerieBO.class);
    }

    public SerieBO serieUpdateDTOToBO(SerieUpdateDTO serieUpdateDTO) {
        return modelMapper.map(serieUpdateDTO, SerieBO.class);
    }

    public ProducerOutDTO producerBOToOutDTO(ProducerBO producerBO) {
        return modelMapper.map(producerBO, ProducerOutDTO.class);
    }

    public ProducerBO producerUpdateDTOToBO(ProducerUpdateDTO producerUpdateDTO) {
        return modelMapper.map(producerUpdateDTO, ProducerBO.class);
    }

    public ProducerBO producerInDTOToBO(ProducerInDTO producerInDTO) {
        return modelMapper.map(producerInDTO, ProducerBO.class);
    }
}
