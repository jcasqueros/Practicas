package com.viewnex.bsan.practica04.dto;

import com.viewnex.bsan.practica04.bo.ActorBo;
import com.viewnex.bsan.practica04.bo.DirectorBo;
import com.viewnex.bsan.practica04.bo.ProductionCompanyBo;
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

    DirectorBo director;

    ProductionCompanyBo productionCompany;

    Set<ActorBo> actors;

}