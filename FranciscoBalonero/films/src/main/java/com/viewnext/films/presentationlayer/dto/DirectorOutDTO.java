package com.viewnext.films.presentationlayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing Director information.
 *
 * @author Franciosco Balonero Olivera
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing Director information")

public class DirectorOutDTO {

    @Schema(description = "Id of the director", example = "1")
    private Long id;

    @Schema(description = "Name of the director", example = "Kiko")
    private String name;

    @Schema(description = "Age of the director", example = "22")
    private int age;

    @Schema(description = "Nationality of the director", example = "Spain")
    private String nationality;

}
