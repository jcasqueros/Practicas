package com.viewnext.films.presentationlayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing Producer information.
 *
 * @author Franciosco Balonero Olivera
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing Producer information")
public class ProducerOutDTO {

    @Schema(description = "Id of the producer", example = "1")
    private long id;

    @Schema(description = "Name of the producer", example = "Paramount")
    private String name;

    @Schema(description = "Foundation year of the producer", example = "1998")
    private int foundationYear;
}
