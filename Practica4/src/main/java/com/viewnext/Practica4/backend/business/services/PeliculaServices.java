package com.viewnext.Practica4.backend.business.services;

import java.util.List;

import com.viewnext.Practica4.backend.business.bo.PeliculaBo;
import com.viewnext.Practica4.backend.business.model.Pelicula;

public interface PeliculaServices {

	//JPA
	PeliculaBo create(PeliculaBo peliculaBo);
	PeliculaBo read(long id);
	List<PeliculaBo> getAll();
	PeliculaBo update(PeliculaBo peliculaBo);
	public void delete(long id);

	//CRITERIA BUILDER
	PeliculaBo createCb(PeliculaBo peliculaBo);
    PeliculaBo readCb(long id);
    List<PeliculaBo> getPeliculasCb();
    PeliculaBo updateCb(PeliculaBo peliculaBo);
    void deleteCb(long id);
}
