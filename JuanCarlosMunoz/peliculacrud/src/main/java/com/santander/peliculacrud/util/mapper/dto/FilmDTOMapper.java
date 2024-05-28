package com.santander.peliculacrud.util.mapper.dto;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.bo.FilmBO;
import com.santander.peliculacrud.model.dto.FilmDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Film dto mapper.
 */
@Mapper(componentModel = "spring", uses = ActorDTOMapper.class)
@Component
public interface FilmDTOMapper {

    /**
     * Bo to dto film dto.
     *
     * @param filmBO
     *         the film bo
     * @return the film dto
     */
    @Mapping(target = "idActor", source = "actors", qualifiedByName = "actorsToids")
    @Mapping(target = "idDirector", source = "director", qualifiedByName = "directorToId")
    FilmDTO boToDTO(FilmBO filmBO);

    /**
     * Dto to bo film bo.
     *
     * @param filmDTO
     *         the film dto
     * @return the film bo
     */
    @Mapping(target = "actors", source = "idActor", qualifiedByName = "idsToActors")
    @Mapping(target = "director", source = "idDirector", qualifiedByName = "idToDirector")
    @Mapping(target = "id", ignore = true)
    FilmBO dtoToBo(FilmDTO filmDTO);

    /**
     * Bos to dtos list.
     *
     * @param filmBOS
     *         the film bos
     * @return the list
     */
    List<FilmDTO> bosToDtos(List<FilmBO> filmBOS);

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
     * idDirector to director  bo.
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
