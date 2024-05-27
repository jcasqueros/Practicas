package com.viewnext.bsan.practica04.business.bo;

import lombok.*;

import java.util.Set;

/**
 * The {@code FilmBo} business object class represents the films registered in the system at the service layer level.
 *
 * @author Antonio Gil
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FilmBo {

    @EqualsAndHashCode.Include
    long id;

    String title;

    int year;

    DirectorBo director;

    ProductionCompanyBo productionCompany;

    Set<ActorBo> actors;

}
