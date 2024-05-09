package com.pracs.films.presentation.dto;

import com.pracs.films.persistence.models.Actor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object of {@link Actor}
 *
 * @author Manuel Mateos de Torres
 */
@Data
@NoArgsConstructor
public class ActorDtoIn {

    private long id;

    @NotBlank
    private String name;

    @NotNull
    @Min(1)
    @Max(100)
    private int age;

    @NotBlank
    private String nationality;
}
