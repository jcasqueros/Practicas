package com.viewnext.bsan.practica04.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ActorUpsertDto {

    @EqualsAndHashCode.Include
    long id;

    String name;

    int age;

    String nationality;

}
