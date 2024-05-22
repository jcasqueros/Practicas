package com.viewnext.springbatch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

/**
 * Represents a Calle entity in the MongoDB database.
 *
 * @author Francisco Balonero Olivera
 */
@Document(collection = "calles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Calle {

    /**
     * The unique identifier for the Calle entity.
     */
    @Id
    private ObjectId id;

    /**
     * The codigo_calle field of the Calle entity.
     */
    @Field("codigo_calle")
    private Integer codigoCalle;

    /**
     * The tipo_via field of the Calle entity.
     */
    @Field("tipo_via")
    private String tipoVia;

    /**
     * The nombre_calle field of the Calle entity.
     */
    @Field("nombre_calle")
    private String nombreCalle;

    /**
     * The primer_num_tramo field of the Calle entity.
     */
    @Field("primer_num_tramo")
    private Integer primerNumTramo;

    /**
     * The ultimo_num_tramo field of the Calle entity.
     */
    @Field("ultimo_num_tramo")
    private Integer ultimoNumTramo;

    /**
     * The barrio field of the Calle entity.
     */
    @Field("barrio")
    private String barrio;

    /**
     * The cod_distrito field of the Calle entity.
     */
    @Field("cod_distrito")
    private Integer codDistrito;

    /**
     * The nom_distrito field of the Calle entity.
     */
    @Field("nom_distrito")
    private String nomDistrito;
}

