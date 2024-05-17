package com.pracs.films.presentation.dto;

import com.pracs.films.persistence.models.Producer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object of {@link Producer}
 *
 * @author Manuel Mateos de Torres
 */
@Data
@NoArgsConstructor
public class ProducerDtoIn {
    
    @NotBlank
    @Schema(description = "Name of the producer", example = "Michael Productions")
    private String name;

    @NotNull
    @Min(1900)
    @Max(2024)
    @Schema(description = "Born age producer´s", example = "1985")
    private int debut;
}
