package com.viewnext.springbatch.service;

import java.util.List;

import org.springframework.batch.item.Chunk;

import com.viewnext.springbatch.model.Direccion;

public interface DireccionService {

	Iterable<Direccion> saveAll(List<Direccion> direccionList);
	public void save(Direccion direccion);
}
