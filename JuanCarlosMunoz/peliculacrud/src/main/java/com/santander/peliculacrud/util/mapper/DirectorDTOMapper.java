package com.santander.peliculacrud.util.mapper;

import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.dto.DirectorDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * The interface Director dto mapper.
 */
@Mapper(componentModel = "spring")
public interface DirectorDTOMapper {

    /**
     * Bo to dto director bo.
     *
     * @param directorDTO
     *         the director dto
     * @return the director bo
     */
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
    List<DirectorDTO> bosToDtos(List<DirectorBO> directorBOS);

}
