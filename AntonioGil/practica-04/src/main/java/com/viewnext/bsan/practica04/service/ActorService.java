package com.viewnext.bsan.practica04.service;

import com.viewnext.bsan.practica04.bo.ActorBo;
import com.viewnext.bsan.practica04.util.request.PersonFilter;
import com.viewnext.bsan.practica04.util.request.QueryOptions;

import java.util.List;
import java.util.Optional;

/**
 * The {@code ActorService} interface is a service class that defines business logic for operations with actors.
 *
 * @author Antonio Gil
 */
public interface ActorService {

    List<ActorBo> getAll(PersonFilter filter, QueryOptions queryOptions);

    ActorBo getById(Optional<Boolean> useCustomRepository);

    ActorBo create(ActorBo actor, Optional<Boolean> useCustomRepository);

    ActorBo update(long id, ActorBo actor, Optional<Boolean> useCustomRepository);

    void validateActor(ActorBo actor);

    void validateName(String name);

    void validateAge(int age);

    void validateNationality(String nationality);

    void deleteById(long id, Optional<Boolean> useCustomRepository);

}
