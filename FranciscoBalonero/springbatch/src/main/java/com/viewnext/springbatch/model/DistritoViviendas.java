package com.viewnext.springbatch.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "distritos_viviendas")
public class DistritoViviendas {
    private String distrito;
    private Long numViviendas;

    // getters and setters
}
