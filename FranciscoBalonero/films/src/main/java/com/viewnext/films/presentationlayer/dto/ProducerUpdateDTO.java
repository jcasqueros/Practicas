package com.viewnext.films.presentationlayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing Producer to update information.
 *
 * @author Franciosco Balonero Olivera
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing Producer to update information")
public class ProducerUpdateDTO {

    @Schema(description = "Id of the producer", example = "1")
    @NotNull
    private long id;

    @Schema(description = "Name of the producer", example = "Paramount")
    @NotBlank
    private String name;

    @Schema(description = "Foundation year of the producer", example = "1998")
    @NotNull
    private int foundationYear;
}
