package com.viewnext.Practica3Crud.util.converter.Bo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viewnext.Practica3Crud.backend.business.bo.UsuarioBo;
import com.viewnext.Practica3Crud.backend.business.model.Usuario;

@Component
public class EntityToBo {

	@Autowired
	ModelMapper mapper;
	
	public UsuarioBo usuarioToBo(Usuario usuario) {
		UsuarioBo usuarioBo = mapper.map(usuario, UsuarioBo.class);
		return usuarioBo;
	}
}
