package com.pracs.films.presentation.dto;

import com.pracs.films.persistence.models.Serie;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object of {@link Serie}
 *
 * @author Manuel Mateos de Torres
 */
@Data
@NoArgsConstructor
public class SerieDtoIn {

    private long id;

    @NotBlank
    @Schema(description = "Title of the serie", example = "Back to the Future")
    private String title;

    @NotNull
    @Min(1900)
    @Max(2024)
    @Schema(description = "Realise age of the serie", example = "1985")
    private int debut;

    @NotNull
    @Schema(description = "Id of the director", example = "1")
    private DirectorDtoIn director;

    @NotNull
    @Schema(description = "Id of the producer", example = "1")
    private ProducerDtoIn producer;

    @NotNull
    @Schema(description = "List of actors", example = "[1, 3, 6, 7]")
    private List<ActorDtoIn> actors;
}
