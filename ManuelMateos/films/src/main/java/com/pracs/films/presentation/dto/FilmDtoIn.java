package com.pracs.films.presentation.dto;

import com.pracs.films.persistence.models.Film;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object of {@link Film}
 *
 * @author Manuel Mateos de Torres
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmDtoIn {

    private long id;

    @NotBlank
    private String title;

    @NotNull
    @Min(1900)
    @Max(2024)
    private int debut;

    @NotNull
    private DirectorDtoIn director;

    @NotNull
    private ProducerDtoIn producer;

    @NotNull
    private List<ActorDtoIn> actors;
}