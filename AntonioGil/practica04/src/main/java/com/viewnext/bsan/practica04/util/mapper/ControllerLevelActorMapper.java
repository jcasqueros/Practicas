package com.viewnext.bsan.practica04.util.mapper;

import com.viewnext.bsan.practica04.business.bo.ActorBo;
import com.viewnext.bsan.practica04.presentation.dto.ActorReadDto;
import com.viewnext.bsan.practica04.presentation.dto.ActorUpsertDto;
import org.mapstruct.Mapper;

/**
 * The {@code ControllerLevelActorMapper} interface is a MapStruct mapper that handles conversions between
 * {@code ActorBo} business objects and {@code ActorReadDto} or {@code ActorUpsertDto} data transfer objects. It is
 * used by controllers to convert between native objects from each layer (business logic and presentation).
 *
 * @author Antonio Gil
 */
@Mapper(componentModel = "spring")
public interface ControllerLevelActorMapper {

    ActorReadDto boToReadDto(ActorBo bo);

    ActorBo dtoToBo(ActorUpsertDto dto);

}
