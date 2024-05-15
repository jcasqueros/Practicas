package com.santander.peliculacrud.util.mapper;

import com.santander.peliculacrud.model.bo.FilmBO;
import com.santander.peliculacrud.model.bo.SeriesBO;
import com.santander.peliculacrud.model.entity.Film;
import com.santander.peliculacrud.model.entity.Series;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * The interface Series bo mapper.
 */
@Mapper(componentModel = "spring")
public interface SeriesBOMapper {

    /**
     * Bo to entity series.
     *
     * @param seriesBO
     *         the series bo
     * @return the series
     */
    @Mapping(source = "actors", target = "actors")
    @Mapping(source = "director", target = "director")
    Series boToEntity(SeriesBO seriesBO);

    /**
     * Entity to bo series bo.
     *
     * @param series
     *         the series
     * @return the series bo
     */
    @Mapping(source = "actors", target = "actors")
    @Mapping(source = "director", target = "director")
    SeriesBO entityToBo(Series series);

    /**
     * List entity list bo list.
     *
     * @param series
     *         the series
     * @return the list
     */
    @Mapping(source = "actors", target = "actors")
    @Mapping(source = "director", target = "director")
    List<SeriesBO> listEntityListBo(List<Series> series);
}