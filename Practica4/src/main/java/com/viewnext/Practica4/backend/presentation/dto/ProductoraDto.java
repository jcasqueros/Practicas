package com.viewnext.Practica4.backend.presentation.dto;

import java.io.Serializable;
import java.util.List;

import com.viewnext.Practica4.backend.business.model.Actor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoraDto {

	Long id;
	String nombre;
	int anhoFundacion;
}
