package com.viewnext.batch01.model;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * The {@code Tramo} class is a model class for the Tramo entity.
 *
 * @author Antonio Gil
 */
@Entity
@Table(name = "TRAMOS")
public class Tramo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRAMO")
    @Field(name = "ID_TRAMO")
    Long id;

    @Column(name = "CODIGO_CALLE")
    @Field(name = "CODIGO_CALLE")
    int codigoCalle;

    @Column(name = "TIPO_VIA")
    @Field(name = "TIPO_VIA")
    String tipoVia;

    @Column(name = "NOMBRE_CALLE")
    @Field(name = "NOMBRE_CALLE")
    String nombreCalle;

    @Column(name = "PRIMER_NUM_TRAMO")
    @Field(name = "PRIMER_NUM_TRAMO")
    int primerNumeroTramo;

    @Column(name = "ULTIMO_NUM_TRAMO")
    @Field(name = "ULTIMO_NUM_TRAMO")
    int ultimoNumeroTramo;

    @Column(name = "BARRIO")
    @Field(name = "BARRIO")
    String barrio;

    @Column(name = "COD_DISTRITO")
    @Field(name = "COD_DISTRITO")
    int codigoDistrito;

    @Column(name = "NOM_DISTRITO")
    @Field(name = "NOM_DISTRITO")
    String nombreDistrito;

    public Tramo(Long id, int codigoCalle, String tipoVia, String nombreCalle, int primerNumeroTramo,
                 int ultimoNumeroTramo, String barrio, int codigoDistrito, String nombreDistrito) {
        this.id = id;
        this.codigoCalle = codigoCalle;
        this.tipoVia = tipoVia;
        this.nombreCalle = nombreCalle;
        this.primerNumeroTramo = primerNumeroTramo;
        this.ultimoNumeroTramo = ultimoNumeroTramo;
        this.barrio = barrio;
        this.codigoDistrito = codigoDistrito;
        this.nombreDistrito = nombreDistrito;
    }

    public Tramo() {
    }

    public static TramoBuilder builder() {
        return new TramoBuilder();
    }

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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCodigoCalle() {
        return this.codigoCalle;
    }

    public void setCodigoCalle(int codigoCalle) {
        this.codigoCalle = codigoCalle;
    }

    public String getTipoVia() {
        return this.tipoVia;
    }

    public void setTipoVia(String tipoVia) {
        this.tipoVia = tipoVia;
    }

    public String getNombreCalle() {
        return this.nombreCalle;
    }

    public void setNombreCalle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }

    public int getPrimerNumeroTramo() {
        return this.primerNumeroTramo;
    }

    public void setPrimerNumeroTramo(int primerNumeroTramo) {
        this.primerNumeroTramo = primerNumeroTramo;
    }

    public int getUltimoNumeroTramo() {
        return this.ultimoNumeroTramo;
    }

    public void setUltimoNumeroTramo(int ultimoNumeroTramo) {
        this.ultimoNumeroTramo = ultimoNumeroTramo;
    }

    public String getBarrio() {
        return this.barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public int getCodigoDistrito() {
        return this.codigoDistrito;
    }

    public void setCodigoDistrito(int codigoDistrito) {
        this.codigoDistrito = codigoDistrito;
    }

    public String getNombreDistrito() {
        return this.nombreDistrito;
    }

    public void setNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tramo.class.getSimpleName() + "[", "]")
                .add("codigoCalle=" + codigoCalle)
                .add("tipoVia='" + tipoVia + "'")
                .add("nombreCalle='" + nombreCalle + "'")
                .add("primerNumeroTramo=" + primerNumeroTramo)
                .add("ultimoNumeroTramo=" + ultimoNumeroTramo)
                .add("barrio='" + barrio + "'")
                .add("codigoDistrito=" + codigoDistrito)
                .add("nombreDistrito='" + nombreDistrito + "'")
                .toString();
    }


    public static class TramoBuilder {

        private Long id;
        private int codigoCalle;
        private String tipoVia;
        private String nombreCalle;
        private int primerNumeroTramo;
        private int ultimoNumeroTramo;
        private String barrio;
        private int codigoDistrito;
        private String nombreDistrito;

        TramoBuilder() {
        }

        public TramoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TramoBuilder codigoCalle(int codigoCalle) {
            this.codigoCalle = codigoCalle;
            return this;
        }

        public TramoBuilder tipoVia(String tipoVia) {
            this.tipoVia = tipoVia;
            return this;
        }

        public TramoBuilder nombreCalle(String nombreCalle) {
            this.nombreCalle = nombreCalle;
            return this;
        }

        public TramoBuilder primerNumeroTramo(int primerNumeroTramo) {
            this.primerNumeroTramo = primerNumeroTramo;
            return this;
        }

        public TramoBuilder ultimoNumeroTramo(int ultimoNumeroTramo) {
            this.ultimoNumeroTramo = ultimoNumeroTramo;
            return this;
        }

        public TramoBuilder barrio(String barrio) {
            this.barrio = barrio;
            return this;
        }

        public TramoBuilder codigoDistrito(int codigoDistrito) {
            this.codigoDistrito = codigoDistrito;
            return this;
        }

        public TramoBuilder nombreDistrito(String nombreDistrito) {
            this.nombreDistrito = nombreDistrito;
            return this;
        }

        public Tramo build() {
            return new Tramo(this.id, this.codigoCalle, this.tipoVia, this.nombreCalle, this.primerNumeroTramo,
                    this.ultimoNumeroTramo, this.barrio, this.codigoDistrito, this.nombreDistrito);
        }

        public String toString() {
            return "Tramo.TramoBuilder(id=" + this.id + ", codigoCalle=" + this.codigoCalle + ", tipoVia=" +
                    this.tipoVia + ", nombreCalle=" + this.nombreCalle + ", primerNumeroTramo=" +
                    this.primerNumeroTramo + ", ultimoNumeroTramo=" + this.ultimoNumeroTramo + ", barrio=" +
                    this.barrio + ", codigoDistrito=" + this.codigoDistrito + ", nombreDistrito=" +
                    this.nombreDistrito + ")";
        }

    }

}
