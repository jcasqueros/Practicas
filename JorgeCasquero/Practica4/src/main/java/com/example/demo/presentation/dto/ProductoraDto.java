package com.example.demo.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;



@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductoraDto {

	private long idProductora;
	private String nombre;
	private int anioFundacion;
}
