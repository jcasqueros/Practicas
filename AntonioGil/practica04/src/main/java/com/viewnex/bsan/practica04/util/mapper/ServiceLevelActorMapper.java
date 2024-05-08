package com.viewnex.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.ActorBo;
import com.viewnex.bsan.practica04.entity.Actor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceLevelActorMapper {

    ActorBo entityToBo(Actor entity);

    Actor boToEntity(ActorBo bo);

}
