package com.viewnext.bsan.practica04.util.mapper;

import com.viewnext.bsan.practica04.bo.FilmBo;
import com.viewnext.bsan.practica04.entity.Film;
import org.mapstruct.Mapper;

/**
 * The {@code ServiceLevelFilmMapper} interface is a MapStruct mapper that handles conversions between {@code Film}
 * entity objects and {@code FilmBo} business objects. It is used by service implementations to convert between
 * native objects from each layer (persistence and business logic).
 *
 * @author Antonio Gil
 */
@Mapper(componentModel = "spring")
public interface ServiceLevelFilmMapper {

    FilmBo entityToBo(Film entity);

    Film boToEntity(FilmBo bo);

}
