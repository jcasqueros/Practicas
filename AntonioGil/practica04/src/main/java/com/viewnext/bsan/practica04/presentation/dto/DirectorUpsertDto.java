package com.viewnext.bsan.practica04.presentation.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DirectorUpsertDto {

    @EqualsAndHashCode.Include
    long id;

    String name;

    int age;

    String nationality;

}
