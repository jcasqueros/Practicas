package com.viewnext.bsan.practica04.util.mapper;

import com.viewnext.bsan.practica04.business.bo.FilmBo;
import com.viewnext.bsan.practica04.presentation.dto.FilmReadDto;
import com.viewnext.bsan.practica04.presentation.dto.FilmUpsertDto;
import org.mapstruct.Mapper;

/**
 * The {@code ControllerLevelFilmMapper} interface is a MapStruct mapper that handles conversions between
 * {@code FilmBo} business objects and {@code FilmReadDto} or {@code FilmUpsertDto} data transfer objects. It is used
 * by controllers to convert between native objects from each layer (business logic and presentation).
 *
 * @author Antonio Gil
 */
@Mapper(componentModel = "spring")
public interface ControllerLevelFilmMapper {

    FilmReadDto boToReadDto(FilmBo bo);

    FilmBo dtoToBo(FilmUpsertDto dto);

}
