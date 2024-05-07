package com.pracs.films.bussiness.converters;

import com.pracs.films.bussiness.bo.*;
import com.pracs.films.persistence.models.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Class that manage the different methods for convert all the Model Objects to BO Objects
 *
 * @author Manuel Mateos de Torres
 */
@RequiredArgsConstructor
@Component
public class ModelToBoConverter {

    private final ModelMapper modelMapper;

    public ActorBO actorModelToBo(Actor actor) {
        return modelMapper.map(actor, ActorBO.class);
    }

    public DirectorBO directorModelToBo(Director director) {
        return modelMapper.map(director, DirectorBO.class);
    }

    public FilmBO filmModelToBo(Film film) {
        return modelMapper.map(film, FilmBO.class);
    }

    public SerieBO serieModelToBo(Serie serie) {
        return modelMapper.map(serie, SerieBO.class);
    }

    public ProducerBO producerModelToBo(Producer producer) {
        return modelMapper.map(producer, ProducerBO.class);
    }
}
