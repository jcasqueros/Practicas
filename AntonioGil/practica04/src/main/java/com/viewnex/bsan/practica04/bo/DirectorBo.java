package com.viewnex.bsan.practica04.bo;

import lombok.*;

/**
 * The {@code DirectorBo} business object class represents the directors registered in the system at the service layer
 * level.
 *
 * @author Antonio Gil
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DirectorBo {

    @EqualsAndHashCode.Include
    long id;

    String name;

    int age;

    String nationality;

}
