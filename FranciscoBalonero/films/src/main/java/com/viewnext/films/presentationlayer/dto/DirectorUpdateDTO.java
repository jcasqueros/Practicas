package com.viewnext.films.presentationlayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing Director to update information.
 *
 * @author Franciosco Balonero Olivera
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing Director to update information")

public class DirectorUpdateDTO {

    @Schema(description = "Id of the director", example = "1")
    @NotNull
    private Long id;

    @Schema(description = "Name of the director", example = "Kiko")
    @NotBlank
    private String name;

    @Schema(description = "Age of the director", example = "22")
    @NotNull
    private int age;

    @Schema(description = "Nationality of the director", example = "Spain")
    @NotBlank
    private String nationality;
}
