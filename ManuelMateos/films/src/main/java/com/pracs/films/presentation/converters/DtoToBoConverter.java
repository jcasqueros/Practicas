package com.pracs.films.presentation.converters;

import com.pracs.films.bussiness.bo.*;
import com.pracs.films.presentation.dto.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Class that manage the different methods for convert all the DTO Objects to BO Objects
 *
 * @author Manuel Mateos de Torres
 */
@RequiredArgsConstructor
@Component
public class DtoToBoConverter {

    private final ModelMapper modelMapper;

    public ActorBO actorDtoToBo(ActorDtoIn actorDTO) {
        return modelMapper.map(actorDTO, ActorBO.class);
    }

    public DirectorBO directorDtoToBo(DirectorDtoIn directorDTO) {
        return modelMapper.map(directorDTO, DirectorBO.class);
    }

    public FilmBO filmDtoToBo(FilmDtoIn filmDTO) {
        return modelMapper.map(filmDTO, FilmBO.class);
    }

    public SerieBO serieDtoToBo(SerieDtoIn serieDTO) {
        return modelMapper.map(serieDTO, SerieBO.class);
    }

    public ProducerBO producerDtoToBo(ProducerDtoIn producerDTO) {
        return modelMapper.map(producerDTO, ProducerBO.class);
    }

    public ActorBO actorDtoUpdateToBo(ActorDtoInUpdate actorDTO) {
        return modelMapper.map(actorDTO, ActorBO.class);
    }

    public DirectorBO directorUpdateDtoToBo(DirectorDtoInUpdate directorDTO) {
        return modelMapper.map(directorDTO, DirectorBO.class);
    }

    public FilmBO filmDtoUpdateToBo(FilmDtoInUpdate filmDTO) {
        return modelMapper.map(filmDTO, FilmBO.class);
    }

    public SerieBO serieDtoUpdateToBo(SerieDtoInUpdate serieDTO) {
        return modelMapper.map(serieDTO, SerieBO.class);
    }

    public ProducerBO producerDtoUpdateToBo(ProducerDtoInUpdate producerDTO) {
        return modelMapper.map(producerDTO, ProducerBO.class);
    }
}
