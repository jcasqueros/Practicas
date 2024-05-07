package com.pracs.films.presentation.converters;

import com.pracs.films.bussiness.bo.*;
import com.pracs.films.presentation.dto.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Class that manage the different methods for convert all the BO Objects to DTO Objects
 *
 * @author Manuel Mateos de Torres
 */
@Data
@RequiredArgsConstructor
@Component
public class BoToDtoConverter {

    private ModelMapper modelMapper;

    public ActorDtoOut actorBoToDtoOut(ActorBO actorBO) {
        return modelMapper.map(actorBO, ActorDtoOut.class);
    }

    public DirectorDtoOut directorBoToDtoOut(DirectorBO directorBO) {
        return modelMapper.map(directorBO, DirectorDtoOut.class);
    }

    public FilmDtoOut filmBoToDtoOut(FilmBO filmBO) {
        return modelMapper.map(filmBO, FilmDtoOut.class);
    }

    public SerieDtoOut serieBoToDtoOut(SerieBO serieBO) {
        return modelMapper.map(serieBO, SerieDtoOut.class);
    }

    public ProducerDtoOut producerBoToDtoOut(ProducerBO producerBO) {
        return modelMapper.map(producerBO, ProducerDtoOut.class);
    }
}
