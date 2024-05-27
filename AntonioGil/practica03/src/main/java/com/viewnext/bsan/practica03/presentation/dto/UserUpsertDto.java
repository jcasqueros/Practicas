package com.viewnext.bsan.practica03.presentation.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserUpsertDto {

    @EqualsAndHashCode.Include
    String dni;

    String name;

    String surname;

    int age;

}
