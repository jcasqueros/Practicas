package com.pracs.films.persistence.repositories.criteria;

import com.pracs.films.persistence.models.Actor;

public interface ActorCustomRepository {

    Actor findById(long id);
}
