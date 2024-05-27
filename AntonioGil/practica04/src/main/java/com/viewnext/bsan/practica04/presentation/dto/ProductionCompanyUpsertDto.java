package com.viewnext.bsan.practica04.presentation.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductionCompanyUpsertDto {

    @EqualsAndHashCode.Include
    long id;

    String name;

    int yearFounded;

}