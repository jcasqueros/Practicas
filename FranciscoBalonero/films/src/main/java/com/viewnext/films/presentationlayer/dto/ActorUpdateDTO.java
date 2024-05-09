package com.viewnext.films.presentationlayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing Actor to update information.
 *
 * @author Franciosco Balonero Olivera
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing Actor to update information")

public class ActorUpdateDTO {

    @Schema(description = "Id of the actor", example = "1")
    @NotNull
    private Long id;

    @Schema(description = "Name of the actor", example = "Kiko")
    @NotBlank
    private String name;

    @Schema(description = "Age of the actor", example = "22")
    @NotNull
    private int age;

    @Schema(description = "Nationality of the actor", example = "Spain")
    @NotBlank
    private String nationality;
}
