package com.viewnext.Practica4.backend.business.services;

import java.util.List;

import com.viewnext.Practica4.backend.business.bo.SerieBo;
import com.viewnext.Practica4.backend.business.model.Serie;

public interface SerieServices {

	//JPA
	SerieBo create(SerieBo serieBo);
	SerieBo read(long id);
	List<SerieBo> getAll();
	SerieBo update(SerieBo serieBo);
	public void delete(long id);

	//CRITERIA BUILDER
	SerieBo createCb(SerieBo serieBo);
    SerieBo readCb(long id);
    List<SerieBo> getSeriesCb();
    SerieBo updateCb(SerieBo serieBo);
    void deleteCb(long id);
}
