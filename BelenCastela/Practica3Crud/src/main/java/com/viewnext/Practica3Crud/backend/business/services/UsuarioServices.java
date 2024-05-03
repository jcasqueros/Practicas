package com.viewnext.Practica3Crud.backend.business.services;

import java.util.List;

import com.viewnext.Practica3Crud.backend.business.bo.UsuarioBo;

public interface UsuarioServices {

	UsuarioBo create(UsuarioBo usuarioBo);
	
	UsuarioBo read(String mDni);
	
	List<UsuarioBo> getAll();
	
	UsuarioBo updateUsuario(UsuarioBo usuarioBo);
	
	void delete(String mDni); 
}
