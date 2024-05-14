package com.viewnext.bsan.practica04.bo;

import lombok.*;

/**
 * The {@code ProductionCompanyBo} business object class represents the production companies registered in the system
 * at the service layer level.
 *
 * @author Antonio Gil
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductionCompanyBo {

    @EqualsAndHashCode.Include
    long id;

    String name;

    int yearFounded;

}
