package com.santander.peliculacrud.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Director out.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DirectorBO {

    private long id;

    private String name;

    private int age;

    private String nation;
}
