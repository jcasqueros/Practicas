package com.viewnex.bsan.practica04.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductionCompanyReadDto {

    @EqualsAndHashCode.Include
    long id;

    String name;

    int yearFounded;

}