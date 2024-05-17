package com.viewnext.Practica3Crud.backend.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viewnext.Practica3Crud.backend.business.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{

	Optional<Usuario> findBymDni(String mDni);
}
