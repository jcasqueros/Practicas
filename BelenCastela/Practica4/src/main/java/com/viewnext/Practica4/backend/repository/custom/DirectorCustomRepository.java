package com.viewnext.Practica4.backend.repository.custom;

import java.util.List;

import com.viewnext.Practica4.backend.business.model.Director;

public interface DirectorCustomRepository {

	Director createCb(Director director);
    Director readCb(long id);
    List<Director> getDirectoresCb();
    Director updateCb(Director director);
    void deleteCb(long id);
}
