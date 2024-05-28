package com.santander.peliculacrud.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Actor bo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActorBO {

    private long id;

    private String name;

    private int age;

    private String nation;

}
