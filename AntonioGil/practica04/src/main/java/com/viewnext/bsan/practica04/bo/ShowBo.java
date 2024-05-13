package com.viewnext.bsan.practica04.bo;

import lombok.*;

import java.util.Set;

/**
 * The {@code ShowBo} business object class represents the shows registered in the system at the service layer level.
 *
 * @author Antonio Gil
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ShowBo {

    @EqualsAndHashCode.Include
    long id;

    String title;

    int year;

    DirectorBo director;

    ProductionCompanyBo productionCompany;

    Set<ActorBo> actors;

}
