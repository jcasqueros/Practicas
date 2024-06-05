package com.viewnext.springbatchf.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The type District.
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class District {

    private String nomDistrito;
    private int numCasas;

}
