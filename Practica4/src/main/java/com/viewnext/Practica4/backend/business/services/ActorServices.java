package com.viewnext.Practica4.backend.business.services;

import java.util.List;

import com.viewnext.Practica4.backend.business.bo.ActorBo;
import com.viewnext.Practica4.backend.business.model.Actor;

public interface ActorServices {

	//JPA
	ActorBo create(ActorBo actorBo);
	ActorBo read(long id);
	List<ActorBo> getAll();
	ActorBo update(ActorBo actorBo);
	public void delete(long id);
	
	//CriteriaBuilder
	public List<ActorBo>getActoresCb();
	ActorBo createCb(ActorBo actorBo);
	ActorBo readCb(long id);
	ActorBo updateCb(ActorBo actorBo);
	void deleteCb(long id);
}
