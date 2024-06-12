package com.viewnext.springbatchf.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The type City.
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class City {

    @Id
    private int codPostal;
    private String name;

}
