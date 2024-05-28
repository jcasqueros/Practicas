package com.viewnext.bsan.practica03.business.bo;

import jakarta.persistence.Entity;
import lombok.*;

/**
 * The {@code UserBo} business object class represents the users registered in the system.
 *
 * @author Antonio Gil
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserBo {

    @EqualsAndHashCode.Include
    String dni;

    String name;

    String surname;

    int age;

}
