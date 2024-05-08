package com.pracs.films.presentation.dto;

import com.pracs.films.persistence.models.Actor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object of {@link Actor}
 *
 * @author Manuel Mateos de Torres
 */
@Data
@NoArgsConstructor
public class ActorDtoOut {

    private long id;

    private String name;

    private int age;

    private String nationality;
}
