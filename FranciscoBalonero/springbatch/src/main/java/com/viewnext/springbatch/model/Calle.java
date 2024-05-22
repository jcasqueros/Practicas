package com.viewnext.springbatch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Document(collection = "calles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Calle {

    @Id
    private ObjectId id;

    @Field("codigo_calle")
    private Integer codigoCalle;

    @Field("tipo_via")
    private String tipoVia;

    @Field("nombre_calle")
    private String nombreCalle;

    @Field("primer_num_tramo")
    private Integer primerNumTramo;

    @Field("ultimo_num_tramo")
    private Integer ultimoNumTramo;

    @Field("barrio")
    private String barrio;

    @Field("cod_distrito")
    private Integer codDistrito;

    @Field("nom_distrito")
    private String nomDistrito;
}

