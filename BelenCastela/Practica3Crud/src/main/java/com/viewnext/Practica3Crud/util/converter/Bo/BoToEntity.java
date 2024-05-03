package com.viewnext.Practica3Crud.util.converter.Bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viewnext.Practica3Crud.backend.business.bo.UsuarioBo;
import com.viewnext.Practica3Crud.backend.business.model.Usuario;

import org.modelmapper.ModelMapper;

@Component
public class BoToEntity {

	@Autowired
	ModelMapper mapper;
	
	public Usuario usuarioBoTousuario (UsuarioBo usuarioBo) {
		Usuario usuario = mapper.map(usuarioBo, Usuario.class);
		return usuario;
	}
	
}
