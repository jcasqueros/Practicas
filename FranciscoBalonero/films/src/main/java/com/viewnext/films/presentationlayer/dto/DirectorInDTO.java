package com.viewnext.films.presentationlayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing Director to save information.
 *
 * @author Franciosco Balonero Olivera
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing Director to save information")

public class DirectorInDTO {

    @Schema(description = "Name of the director", example = "Kiko")
    @NotBlank
    private String name;

    @Schema(description = "Age of the director", example = "22")
    @NotNull
    @Positive
    private int age;

    @Schema(description = "Nationality of the director", example = "Spain")
    @NotBlank
    private String nationality;
}
