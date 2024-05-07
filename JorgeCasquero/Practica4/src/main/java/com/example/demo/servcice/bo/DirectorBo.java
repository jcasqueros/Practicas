package com.example.demo.servcice.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DirectorBo {

	
	private long idDirector;
	private String nombre;
	private int edad;
	private String nacionalidad;
}
