package com.viewnex.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.FilmBo;
import com.viewnex.bsan.practica04.entity.Film;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceLevelFilmMapper {

    FilmBo entityToBo(Film entity);

    Film boToEntity(FilmBo bo);

}
