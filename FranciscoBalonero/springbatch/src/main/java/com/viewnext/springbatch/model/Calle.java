package com.viewnext.springbatch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

/**
 * This class represents a Calle entity.
 *
 * <p>It is annotated with {@link org.springframework.data.mongodb.core.mapping.Document} to specify the collection
 * name.</p>
 *
 * <p>It uses Lombok annotations to generate getters, setters, and constructors.</p>
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
     *
     * <p>This field is annotated with {@link javax.persistence.Id} to specify it as the primary key.</p>
     */
    @Id
    private ObjectId id;

    /**
     * The code for the calle.
     */
    @Field("codigo_calle")
    private Integer codigoCalle;

    /**
     * The type of via for the calle.
     */
    @Field("tipo_via")
    private String tipoVia;

    /**
     * The name of the calle.
     */
    @Field("nombre_calle")
    private String nombreCalle;

    /**
     * The first number of the tramo for the calle.
     */
    @Field("primer_num_tramo")
    private Integer primerNumTramo;

    /**
     * The last number of the tramo for the calle.
     */
    @Field("ultimo_num_tramo")
    private Integer ultimoNumTramo;

    /**
     * The barrio for the calle.
     */
    @Field("barrio")
    private String barrio;

    /**
     * The code for the distrito for the calle.
     */
    @Field("cod_distrito")
    private Integer codDistrito;

    /**
     * The name of the distrito for the calle.
     */
    @Field("nom_distrito")
    private String nomDistrito;
}

