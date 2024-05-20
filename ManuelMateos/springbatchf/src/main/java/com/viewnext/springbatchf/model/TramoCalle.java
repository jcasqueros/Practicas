package com.viewnext.springbatchf.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tramos_calle")
@Entity
public class TramoCalle {

    @Id
    @Column(name = "CALLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "CODIGO_CALLE")
    private long codigo;

    @Column(name = "TIPO_VIA")
    private String via;

    @Column(name = "NOMBRE_CALLE")
    private String nombre;

    @Column(name = "PRIMER_NUM_TRAMO")
    private int primerNum;

    @Column(name = "ULTIMO_NUM_TRAMO")
    private int ultimoNum;

    @Column(name = "BARRIO")
    private String barrio;

    @Column(name = "COD_DISTRITO")
    private int codDistrito;

    @Column(name = "NOM_DISTRITO")
    private String nomDistrito;
}