package com.viewnext.batch01.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TRAMOS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tramo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRAMO")
    Long id;

    @Column(name = "CODIGO_CALLE")
    int codigoCalle;

    @Column(name = "TIPO_VIA")
    String tipoVia;

    @Column(name = "NOMBRE_CALLE")
    String nombreCalle;

    @Column(name = "PRIMER_NUM_TRAMO")
    int primerNumeroTramo;

    @Column(name = "ULTIMO_NUM_TRAMO")
    int ultimoNumeroTramo;

    @Column(name = "BARRIO")
    String barrio;

    @Column(name = "COD_DISTRITO")
    int codigoDistrito;

    @Column(name = "NOM_DISTRITO")
    String nombreDistrito;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tramo tramo = (Tramo) o;
        return Objects.equals(id, tramo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
