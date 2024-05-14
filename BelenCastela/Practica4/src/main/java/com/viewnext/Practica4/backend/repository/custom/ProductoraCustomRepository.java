package com.viewnext.Practica4.backend.repository.custom;

import java.util.List;

import com.viewnext.Practica4.backend.business.model.Productora;

public interface ProductoraCustomRepository {

	Productora createCb(Productora productora);
    Productora readCb(long id);
    List<Productora> getProductorasCb();
    Productora updateCb(Productora productora);
    void deleteCb(long id);
}
