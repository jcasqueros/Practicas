package com.viewnext.bsan.practica04.util.mapper;

import com.viewnext.bsan.practica04.business.bo.ActorBo;
import com.viewnext.bsan.practica04.persistence.entity.Actor;
import org.mapstruct.Mapper;

/**
 * The {@code ServiceLevelActorMapper} interface is a MapStruct mapper that handles conversions between {@code Actor}
 * entity objects and {@code ActorBo} business objects. It is used by service implementations to convert between
 * native objects from each layer (persistence and business logic).
 *
 * @author Antonio Gil
 */
@Mapper(componentModel = "spring")
public interface ServiceLevelActorMapper {

    ActorBo entityToBo(Actor entity);

    Actor boToEntity(ActorBo bo);

}
