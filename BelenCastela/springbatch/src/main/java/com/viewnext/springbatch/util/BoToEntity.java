package com.viewnext.springbatch.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viewnext.springbatch.model.Direccion;
import com.viewnext.springbatch.model.bo.DireccionBo;

@Component
public class BoToEntity {

	@Autowired
	ModelMapper mapper;
	
	Direccion direccionBoToDireccion (DireccionBo direccionBo) {
		Direccion direccion = mapper.map(direccionBo, Direccion.class);
		return direccion;
	}
}
