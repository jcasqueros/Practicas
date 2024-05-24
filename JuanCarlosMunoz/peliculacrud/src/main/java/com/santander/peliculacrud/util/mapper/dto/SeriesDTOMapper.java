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

    @Mapping(target = "idActor", source = "actors", qualifiedByName = "actorsToids")
    @Mapping(target = "idDirector", source = "director", qualifiedByName = "directorToId")
    SeriesDTO boToDTO(SeriesBO seriesBO);

    @Mapping(target = "actors", source = "idActor", qualifiedByName = "idsToActors")
    @Mapping(target = "director", source = "idDirector", qualifiedByName = "idToDirector")
    @Mapping(target = "id", ignore = true)
    SeriesBO dtoToBo(SeriesDTO seriesDTO);

    List<SeriesDTO> bosToDtos(List<SeriesBO> seriesBOS);

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
