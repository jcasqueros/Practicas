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

    @Mapping(target = "idActor", source = "actors", qualifiedByName = "actorsToids")
    @Mapping(target = "idDirector", source = "director", qualifiedByName = "directorToId")
    FilmDTO boToDTO(FilmBO filmBO);

    @Mapping(target = "actors", source = "idActor", qualifiedByName = "idsToActors")
    @Mapping(target = "director", source = "idDirector", qualifiedByName = "idToDirector")
    @Mapping(target = "id", ignore = true)
    FilmBO dtoToBo(FilmDTO filmDTO);

    List<FilmDTO> bosToDtos(List<FilmBO> filmBOS);

    @Named("idsToActors")
    default List<ActorBO> idsToActors(List<Long> idActors) {
        return idActors.stream()
                .map(id -> ActorBO.builder().id(id).build())
                .toList();
    }

    @Named("idToDirector")
    default DirectorBO idToDirector(Long idDirector) {
        return DirectorBO.builder().id(idDirector).build();
    }

    @Named("actorsToids")
    default List<Long>  actorsToids(List<ActorBO> actorBOS) {
        return actorBOS.stream()
                .map(ActorBO::getId)
                .toList();
    }

    @Named("directorToId")
    default Long  directorToId(DirectorBO directorBO) {
        return directorBO.getId();
    }
}
