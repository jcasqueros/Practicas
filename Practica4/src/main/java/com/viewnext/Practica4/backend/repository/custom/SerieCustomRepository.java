package com.viewnext.Practica4.backend.repository.custom;

import java.util.List;

import com.viewnext.Practica4.backend.business.model.Serie;

public interface SerieCustomRepository {

	Serie createCb(Serie serie);
    Serie readCb(long id);
    List<Serie> getSeriesCb();
    Serie updateCb(Serie serie);
    void deleteCb(long id);
}
