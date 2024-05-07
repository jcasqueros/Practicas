package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Productora {

	@Id
	@Column(name = "id_productora")
	private long idProductora;
	private String nombre;

	@Column(name = "anio_fundacion")
	private int anioFundacion;

}
