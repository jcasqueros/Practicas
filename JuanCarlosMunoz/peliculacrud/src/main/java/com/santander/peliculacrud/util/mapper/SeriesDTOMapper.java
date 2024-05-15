package com.santander.peliculacrud.util.mapper;

import com.santander.peliculacrud.model.bo.SeriesBO;
import com.santander.peliculacrud.model.dto.SeriesDTO;

import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The interface Series dto mapper.
 */
@Service
public interface SeriesDTOMapper {

    /**
     * Dto to bo series dto.
     *
     * @param seriesBO
     *         the series bo
     * @return the series dto
     */
    @Mapping(source = "actors", target = "actors")
    @Mapping(source = "director", target = "director")
    SeriesDTO boToDTO(SeriesBO seriesBO);

    /**
     * Bo to dto series bo.
     *
     * @param seriesDTO
     *         the series dto
     * @return the series bo
     */
    @Mapping(source = "actors", target = "actors")
    @Mapping(source = "director", target = "director")
    SeriesBO dtoToBo(SeriesDTO seriesDTO);

    /**
     * List bos to dtos list.
     *
     * @param seriesBOS
     *         the series bos
     * @return the list
     */
    @Mapping(source = "actors", target = "actors")
    @Mapping(source = "director", target = "director")
    List<SeriesDTO> bosToDtos(List<SeriesBO> seriesBOS);

}
