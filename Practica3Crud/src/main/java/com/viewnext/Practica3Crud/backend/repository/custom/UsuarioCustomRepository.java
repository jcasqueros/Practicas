package com.viewnext.Practica3Crud.backend.repository.custom;

import java.util.List;

import com.viewnext.Practica3Crud.backend.business.model.Usuario;

public interface UsuarioCustomRepository {
	List<Usuario> getUsersByAgeName (List<Integer> edades, List<String> nombres);
}
