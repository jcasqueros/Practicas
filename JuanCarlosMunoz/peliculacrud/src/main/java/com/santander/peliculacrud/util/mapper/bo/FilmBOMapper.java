package com.santander.peliculacrud.util.mapper.bo;

import com.santander.peliculacrud.model.bo.FilmBO;
import com.santander.peliculacrud.model.entity.Film;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Film bo mapper.
 */
@Mapper(componentModel = "spring")
@Component
public interface FilmBOMapper {

    /**
     * Bo to entity film.
     *
     * @param filmBO
     *         the film bo
     * @return the film
     */
    @Mapping(source = "actors", target = "actors")
    @Mapping(source = "director", target = "director")
    Film boToEntity(FilmBO filmBO);

    /**
     * Entity to bo film bo.
     *
     * @param film
     *         the film
     * @return the film bo
     */
    @Mapping(source = "actors", target = "actors")
    @Mapping(source = "director", target = "director")
    FilmBO entityToBo(Film film);

    /**
     * List entity list bo list.
     *
     * @param films
     *         the films
     * @return the list
     */
    @Mapping(source = "actors", target = "actors")
    @Mapping(source = "director", target = "director")
    List<FilmBO> listEntityListBo(Page<Film> films);

}
