package com.viewnex.bsan.practica04.bo;

import com.viewnex.bsan.practica04.entity.Actor;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Show {

    @EqualsAndHashCode.Include
    long id;

    String title;

    int year;

    DirectorBo director;

    ProductionCompanyBo productionCompany;

    Set<Actor> actors;

}
