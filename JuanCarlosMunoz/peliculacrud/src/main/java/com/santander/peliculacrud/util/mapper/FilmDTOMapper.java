package com.santander.peliculacrud.util.mapper;

import com.santander.peliculacrud.model.bo.FilmBO;
import com.santander.peliculacrud.model.dto.FilmDTO;

import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The interface Film dto mapper.
 */
@Service
public interface FilmDTOMapper {

    /**
     * Dto to bo film dto.
     *
     * @param filmBO
     *         the film bo
     * @return the film dto
     */
    @Mapping(source = "actors", target = "actors")
    @Mapping(source = "director", target = "director")
    FilmDTO boToDTO(FilmBO filmBO);

    /**
     * Bo to dto film bo.
     *
     * @param filmDTO
     *         the film dto
     * @return the film bo
     */
    @Mapping(source = "actors", target = "actors")
    @Mapping(source = "director", target = "director")
    FilmBO dtoToBo(FilmDTO filmDTO);

    /**
     * List bos to dtos list.
     *
     * @param filmBOS
     *         the film bos
     * @return the list
     */
    @Mapping(source = "actors", target = "actors")
    @Mapping(source = "director", target = "director")
    List<FilmDTO> bosToDtos(List<FilmBO> filmBOS);

}
