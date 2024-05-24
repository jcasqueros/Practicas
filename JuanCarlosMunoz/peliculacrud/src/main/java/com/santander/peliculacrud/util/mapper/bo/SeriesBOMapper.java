package com.santander.peliculacrud.util.mapper.bo;

import com.santander.peliculacrud.model.bo.SeriesBO;
import com.santander.peliculacrud.model.entity.Series;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Series bo mapper.
 */
@Mapper(componentModel = "spring")
@Component
public interface SeriesBOMapper {

    /**
     * Bo to entity film.
     *
     * @param filmBO
     *         the film bo
     * @return the film
     */
    @Mapping(source = "actors", target = "actors")
    @Mapping(source = "director", target = "director")
    Series boToEntity(SeriesBO filmBO);

    /**
     * Entity to bo film bo.
     *
     * @param film
     *         the film
     * @return the film bo
     */
    @Mapping(source = "actors", target = "actors")
    @Mapping(source = "director", target = "director")
    SeriesBO entityToBo(Series film);

    /**
     * List entity list bo list.
     *
     * @param films
     *         the films
     * @return the list
     */
    @Mapping(source = "actors", target = "actors")
    @Mapping(source = "director", target = "director")
    List<SeriesBO> listEntityListBo(Page<Series> films);

}
