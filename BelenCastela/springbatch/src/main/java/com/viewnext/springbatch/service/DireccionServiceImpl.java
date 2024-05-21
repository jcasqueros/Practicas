package com.viewnext.springbatch.service;

import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viewnext.springbatch.model.Direccion;
import com.viewnext.springbatch.repository.DireccionDAO;

@Service
public class DireccionServiceImpl implements DireccionService{
	
	@Autowired
	private DireccionDAO direccionDAO;

	@Override
	public Iterable<Direccion> saveAll(List<Direccion> direccionList) {
		return direccionDAO.saveAll(direccionList);
	}

}
