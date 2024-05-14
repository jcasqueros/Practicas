package com.santander.peliculacrud.util.mapper;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.entity.Actor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * The interface Actor bo mapper.
 */
@Mapper(componentModel = "spring")
public interface ActorBOMapper {

    /**
     * Bo to entity actor.
     *
     * @param actorBO
     *         the actor bo
     * @return the actor
     */
    @Mapping(target = "id", ignore = true)
    Actor boToEntity(ActorBO actorBO);

    /**
     * Entity to bo actor bo.
     *
     * @param actor
     *         the actor
     * @return the actor bo
     */
    ActorBO entityToBo(Actor actor);

    /**
     * List entityto list bo list.
     *
     * @param actors
     *         the actors
     * @return the list
     */
    List<ActorBO> listEntitytoListBo(List<Actor> actors);

}
