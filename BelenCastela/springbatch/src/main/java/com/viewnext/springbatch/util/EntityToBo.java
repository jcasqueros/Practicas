package com.viewnext.springbatch.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viewnext.springbatch.model.Direccion;
import com.viewnext.springbatch.model.bo.DireccionBo;

@Component
public class EntityToBo {

	@Autowired
	ModelMapper mapper;
	
	public DireccionBo direccionToDireccionBo(Direccion direccion) {
		DireccionBo direccionBo = mapper.map(direccion, DireccionBo.class);
		return direccionBo;
	}
}
