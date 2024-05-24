package com.santander.peliculacrud.util.mapper.dto;

import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.dto.DirectorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Director dto mapper.
 */
@Mapper(componentModel = "spring")
@Component
public interface DirectorDTOMapper {

    /**
     * Bo to dto director bo.
     *
     * @param directorDTO
     *         the director dto
     * @return the director bo
     */
    @Mapping(target = "id", ignore = true)
    DirectorBO dtoToBo(DirectorDTO directorDTO);

    /**
     * Dto to bo director dto.
     *
     * @param directorBO
     *         the director bo
     * @return the director dto
     */
    DirectorDTO boToDto(DirectorBO directorBO);

    /**
     * Bos to dtos list.
     *
     * @param directorBOS
     *         the director bos
     * @return the list
     */
    @Mapping(target = "id", ignore = true)
    List<DirectorDTO> bosToDtos(List<DirectorBO> directorBOS);

}
