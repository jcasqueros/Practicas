package com.viewnext.springbatch.service.impl;

import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viewnext.springbatch.model.Direccion;
import com.viewnext.springbatch.repository.DireccionDAO;
import com.viewnext.springbatch.service.DireccionService;

@Service
public class DireccionServiceImpl implements DireccionService{
	
	@Autowired
	private DireccionDAO direccionDAO;

	@Override
	public Iterable<Direccion> saveAll(List<Direccion> direccionList) {
		return direccionDAO.saveAll(direccionList);
	}
	
	 @Override
	    public void save(Direccion direccion) {
	        direccionDAO.save(direccion);
	    }

}
