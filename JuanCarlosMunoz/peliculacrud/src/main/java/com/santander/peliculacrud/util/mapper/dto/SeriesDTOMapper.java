package com.santander.peliculacrud.util.mapper.dto;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.bo.SeriesBO;
import com.santander.peliculacrud.model.dto.SeriesDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Series dto mapper.
 */
@Mapper(componentModel = "spring", uses = ActorDTOMapper.class)
@Component
public interface SeriesDTOMapper {

    /**
     * Bo to dto series dto.
     *
     * @param seriesBO
     *         the series bo
     * @return the series dto
     */
    @Mapping(target = "idActor", source = "actors", qualifiedByName = "actorsToids")
    @Mapping(target = "idDirector", source = "director", qualifiedByName = "directorToId")
    SeriesDTO boToDTO(SeriesBO seriesBO);

    /**
     * Dto to bo series bo.
     *
     * @param seriesDTO
     *         the series dto
     * @return the series bo
     */
    @Mapping(target = "actors", source = "idActor", qualifiedByName = "idsToActors")
    @Mapping(target = "director", source = "idDirector", qualifiedByName = "idToDirector")
    @Mapping(target = "id", ignore = true)
    SeriesBO dtoToBo(SeriesDTO seriesDTO);

    /**
     * Bos to dtos list.
     *
     * @param seriesBOS
     *         the series bos
     * @return the list
     */
    List<SeriesDTO> bosToDtos(List<SeriesBO> seriesBOS);

    /**
     * Ids to actors list.
     *
     * @param idActors
     *         the id actors
     * @return the list
     */
    @Named("idsToActors")
    default List<ActorBO> idsToActors(List<Long> idActors) {
        return idActors.stream().map(id -> ActorBO.builder().id(id).build()).toList();
    }

    /**
     * idToDirector to  director bo.
     *
     * @param idDirector
     *         the id director
     * @return the director bo
     */
    @Named("idToDirector")
    default DirectorBO idToDirector(Long idDirector) {
        return DirectorBO.builder().id(idDirector).build();
    }

    /**
     * Actors toids list.
     *
     * @param actorBOS
     *         the actor bos
     * @return the list
     */
    @Named("actorsToids")
    default List<Long> actorsToids(List<ActorBO> actorBOS) {
        return actorBOS.stream().map(ActorBO::getId).toList();
    }

    /**
     * Director to id long.
     *
     * @param directorBO
     *         the director bo
     * @return the long
     */
    @Named("directorToId")
    default Long directorToId(DirectorBO directorBO) {
        return directorBO.getId();
    }
}
