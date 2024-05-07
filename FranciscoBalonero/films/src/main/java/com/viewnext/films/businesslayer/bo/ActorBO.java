package com.viewnext.films.businesslayer.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Actor business object.
 *
 * @author Francisco Balonero Olivera
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorBO {
    private Long id;

    private String name;

    private int age;

    private String nationality;
}
