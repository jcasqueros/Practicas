package com.pracs.films.bussiness.converters;

import com.pracs.films.bussiness.bo.*;
import com.pracs.films.persistence.models.*;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Class that manage the different methods for convert all the BO Objects to Model Objects
 *
 * @author Manuel Mateos de Torres
 */
@Data
@Component
public class BoToModelConverter {

    private ModelMapper modelMapper;

    public Actor actorBoToModel(ActorBO actorBO) {
        return modelMapper.map(actorBO, Actor.class);
    }

    public Director directorBoToModel(DirectorBO directorBO) {
        return modelMapper.map(directorBO, Director.class);
    }

    public Film filmBoToModel(FilmBO filmBO) {
        return modelMapper.map(filmBO, Film.class);
    }

    public Serie serieBoToModel(SerieBO serieBO) {
        return modelMapper.map(serieBO, Serie.class);
    }

    public Producer producerBoToModel(ProducerBO producerBO) {
        return modelMapper.map(producerBO, Producer.class);
    }
}
