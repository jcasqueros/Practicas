package com.example.demo.presentation.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DirectorDto {

	private long idDirector;
	private String nombre;
	private int edad;
	private String nacionalidad;
}
