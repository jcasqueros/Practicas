package com.viewnex.bsan.practica04.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DirectorReadDto {

    @EqualsAndHashCode.Include
    long id;

    String name;

    int age;

    String nationality;

}
