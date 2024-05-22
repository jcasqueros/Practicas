package com.viewnext.springbatch.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DireccionBo {
	
	private Long id;
	
	private int codigoCalle;
    private String tipoVia;
    private String nombreCalle;
    private int primerNumTramo;
    private int ultimoNumTramo;
    private String barrio;
    private int codDistrito;
    private String nomDistrito;
}
