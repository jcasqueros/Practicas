package com.santander.peliculacrud.util.mapper.dto;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.dto.ActorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Actor dto mapper.
 */
@Mapper(componentModel = "spring")
@Component
public interface ActorDTOMapper {

    /**
     * Bo to dto actor bo.
     *
     * @param actorDTO
     *         the actor dto
     * @return the actor bo
     */
    @Mapping(target = "id", ignore = true)
    ActorBO dtoToBo(ActorDTO actorDTO);

    /**
     * Dto to bo actor dto.
     *
     * @param actorBO
     *         the actor bo
     * @return the actor dto
     */
    ActorDTO boToDto(ActorBO actorBO);

    /**
     * Bos to dtos list.
     *
     * @param actorBOS
     *         the actor bos
     * @return the list
     */
    List<ActorDTO> bosToDtos(List<ActorBO> actorBOS);

}