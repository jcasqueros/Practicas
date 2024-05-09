package com.viewnext.Practica4.backend.business.services;

import java.util.List;

import com.viewnext.Practica4.backend.business.bo.DirectorBo;
import com.viewnext.Practica4.backend.business.model.Director;

public interface DirectorServices {
	//JPA
    DirectorBo create(DirectorBo directorBo);
    DirectorBo read(long id); 
    List<DirectorBo> getAll();
    DirectorBo update(DirectorBo directorBo);
    public void delete(long id);
    
    //CRITERIA BUILDER
    DirectorBo createCb(DirectorBo directorBo);
    DirectorBo readCb(long id);
    List<DirectorBo> getDirectoresCb();
    DirectorBo updateCb(DirectorBo directorBo);
    void deleteCb(long id);
}
