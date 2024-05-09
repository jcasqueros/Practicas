package com.viewnext.films.presentationlayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing Actor information.
 *
 * @author Franciosco Balonero Olivera
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing Actor information")

public class ActorOutDTO {

    @Schema(description = "Id of the actor", example = "1")
    private Long id;

    @Schema(description = "Name of the actor", example = "Kiko")
    private String name;

    @Schema(description = "Age of the actor", example = "22")
    private int age;

    @Schema(description = "Nationality of the actor", example = "Spain")
    private String nationality;
}
