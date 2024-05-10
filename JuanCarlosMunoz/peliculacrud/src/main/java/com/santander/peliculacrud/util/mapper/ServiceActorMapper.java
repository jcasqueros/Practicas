package com.santander.peliculacrud.util.mapper;

import com.santander.peliculacrud.model.input.Actor;
import com.santander.peliculacrud.model.output.ActorModelService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceActorMapper {

    @Mapping(target = "id", ignore = true)
    Actor boToEntity (ActorModelService actorModelService);

    ActorModelService entityToBo (Actor actor);

}
