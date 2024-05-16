package com.pracs.films.presentation.dto;

import com.pracs.films.persistence.models.Director;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object of {@link Director}
 *
 * @author Manuel Mateos de Torres
 */
@Data
@NoArgsConstructor
public class DirectorDtoIn {
    
    @NotBlank
    @Schema(description = "Name of the director", example = "Michael")
    private String name;

    @NotNull
    @Min(1)
    @Max(100)
    @Schema(description = "Age of the director", example = "25")
    private int age;

    @NotBlank
    @Schema(description = "Country of the actor", example = "Spain")
    private String nationality;
}
