package com.viewnext.springbatchf.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The type Street.
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Street {

    private String codigoCalle;
    private String tipoVia;
    private String nombreCalle;
    private int primerNumeroTramo;
    private int ultimoNumeroTramo;
    private String barrio;
    private int codDistrito;
    private String nomDistrito;
}

