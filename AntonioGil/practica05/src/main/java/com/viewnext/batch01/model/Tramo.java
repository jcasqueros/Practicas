package com.viewnext.batch01.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "TRAMOS")
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

    public int getCodigoCalle() {
        return this.codigoCalle;
    }

    public String getTipoVia() {
        return this.tipoVia;
    }

    public String getNombreCalle() {
        return this.nombreCalle;
    }

    public int getPrimerNumeroTramo() {
        return this.primerNumeroTramo;
    }

    public int getUltimoNumeroTramo() {
        return this.ultimoNumeroTramo;
    }

    public String getBarrio() {
        return this.barrio;
    }

    public int getCodigoDistrito() {
        return this.codigoDistrito;
    }

    public String getNombreDistrito() {
        return this.nombreDistrito;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCodigoCalle(int codigoCalle) {
        this.codigoCalle = codigoCalle;
    }

    public void setTipoVia(String tipoVia) {
        this.tipoVia = tipoVia;
    }

    public void setNombreCalle(String nombreCalle) {
        this.nombreCalle = nombreCalle;
    }

    public void setPrimerNumeroTramo(int primerNumeroTramo) {
        this.primerNumeroTramo = primerNumeroTramo;
    }

    public void setUltimoNumeroTramo(int ultimoNumeroTramo) {
        this.ultimoNumeroTramo = ultimoNumeroTramo;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public void setCodigoDistrito(int codigoDistrito) {
        this.codigoDistrito = codigoDistrito;
    }

    public void setNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tramo.class.getSimpleName() + "[", "]")
                .add("id=" + id)
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
