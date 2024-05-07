package com.viewnex.bsan.practica04.bo;

import lombok.*;

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
