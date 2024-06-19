package com.santander.peliculacrud.util.mapper.bo;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.entity.Actor;

import org.springframework.data.domain.Page;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Actor bo mapper.
 */
@Mapper(componentModel = "spring")
@Component
public interface ActorBOMapper {

    /**
     * Bo to entity actor.
     *
     * @param actorBO
     *         the actor bo
     * @return the actor
     */
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
     * @param actorsPage
     *         the actors page
     * @return the list
     */
    List<ActorBO> listEntitytoListBo(Page<Actor> actorsPage);

    List<Actor> listBoToEntity(List<ActorBO> actorsBO);
}
