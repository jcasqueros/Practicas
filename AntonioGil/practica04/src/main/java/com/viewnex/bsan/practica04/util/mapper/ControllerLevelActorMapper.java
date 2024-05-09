package com.viewnex.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.ActorBo;
import com.viewnex.bsan.practica04.dto.ActorReadDto;
import com.viewnex.bsan.practica04.dto.ActorUpsertDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ControllerLevelActorMapper {

    ActorReadDto boToReadDto(ActorBo bo);

    ActorBo dtoToBo(ActorUpsertDto dto);

}
