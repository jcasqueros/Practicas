package com.viewnext.springbatchf.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "calles")
public class Calle {

    @Id
    private ObjectId id;

    @Field("CODIGO_CALLE")
    private long codigo;

    @Field("TIPO_VIA")
    private String via;

    @Field("NOMBRE_CALLE")
    private String nombre;

    @Field("PRIMER_NUM_TRAMO")
    private int primerNum;

    @Field("ULTIMO_NUM_TRAMO")
    private int ultimoNum;

    @Field("BARRIO")
    private String barrio;

    @Field("COD_DISTRITO")
    private int codDistrito;

    @Field("NOM_DISTRITO")
    private String nomDistrito;
}