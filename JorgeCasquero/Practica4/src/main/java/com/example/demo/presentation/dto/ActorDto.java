package com.example.demo.presentation.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ActorDto {
	
	private long idActor;
	private String nombre;
	private int edad;
	private String nacionalidad;

}
