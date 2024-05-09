package com.viewnext.Practica4.backend.repository.custom;

import java.util.List;

import com.viewnext.Practica4.backend.business.bo.ActorBo;
import com.viewnext.Practica4.backend.business.model.Actor;

public interface ActorCustomRepository {
	
	Actor createCb(Actor actor);
    Actor readCb(long id);
    List<Actor> getActoresCb();
    Actor updateCb(Actor actor);
    void deleteCb(long id);
}
