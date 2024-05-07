package com.example.demo.servcice.bo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ActorBo {
	
	
	private long idActor;
	private String nombre;
	private int edad;
	private String nacionalidad;

}
