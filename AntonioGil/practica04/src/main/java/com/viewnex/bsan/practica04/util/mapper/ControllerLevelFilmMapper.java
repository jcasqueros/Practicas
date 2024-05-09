package com.viewnex.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.FilmBo;
import com.viewnex.bsan.practica04.dto.FilmReadDto;
import com.viewnex.bsan.practica04.dto.FilmUpsertDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ControllerLevelFilmMapper {

    FilmReadDto boToReadDto(FilmBo bo);

    FilmBo dtoToBo(FilmUpsertDto dto);

}
