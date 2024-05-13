package com.viewnex.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.FilmBo;
import com.viewnex.bsan.practica04.dto.FilmReadDto;
import com.viewnex.bsan.practica04.dto.FilmUpsertDto;
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
