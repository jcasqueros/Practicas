package com.viewnext.springbatch.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents a DistritoViviendas entity.
 *
 * <p>It is annotated with {@link org.springframework.data.mongodb.core.mapping.Document} to specify the collection
 * name.</p>
 *
 * <p>It uses Lombok's {@link Data} annotation to generate getters, setters, and other boilerplate code.</p>
 *
 * @author Francisco Balonero Olivera
 */
@Data
@Document(collection = "distritos_viviendas")
public class DistritoViviendas {

    /**
     * The distrito name.
     */
    private String distrito;

    /**
     * The number of viviendas in the distrito.
     */
    private Long numViviendas;
}

