package com.viewnext.Practica4.backend.repository.custom;

import java.util.List;

import com.viewnext.Practica4.backend.business.model.Pelicula;

public interface PeliculaCustomRepository {
	Pelicula createCb(Pelicula pelicula);
    Pelicula readCb(long id);
    List<Pelicula> getPeliculasCb();
    Pelicula updateCb(Pelicula pelicula);
    void deleteCb(long id);
}
