package com.viewnex.bsan.practica04.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FilmUpsertDto {

    @EqualsAndHashCode.Include
    long id;

    String title;

    int year;

    DirectorUpsertDto director;

    ProductionCompanyUpsertDto productionCompany;

    Set<ActorUpsertDto> actors;

}
