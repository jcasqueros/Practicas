package com.viewnext.bsan.practica04.bo;

import lombok.*;

/**
 * The {@code ActorBo} business object class represents the actors registered in the system at the service layer level.
 *
 * @author Antonio Gil
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ActorBo {

    @EqualsAndHashCode.Include
    long id;

    String name;

    int age;

    String nationality;

}
