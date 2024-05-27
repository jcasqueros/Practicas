package com.viewnext.bsan.practica04.presentation.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ShowReadDto {

    @EqualsAndHashCode.Include
    long id;

    String title;

    int year;

    DirectorReadDto director;

    ProductionCompanyReadDto productionCompany;

    Set<ActorReadDto> actors;

}