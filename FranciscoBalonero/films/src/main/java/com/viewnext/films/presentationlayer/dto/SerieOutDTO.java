package com.viewnext.films.presentationlayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO representing Serie information.
 *
 * @author Franciosco Balonero Olivera
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing Serie information")

public class SerieOutDTO {

    @Schema(description = "Id of the serie", example = "1")
    private long id;

    @Schema(description = "Title of the serie", example = "Friends")
    private String title;

    @Schema(description = "Release year of the serie", example = "2006")
    private int releaseYear;

    @Schema(description = "Director of the serie")
    private DirectorOutDTO director;

    @Schema(description = "Producer of the serie")
    private ProducerOutDTO producer;

    @Schema(description = "Actors of the serie")
    private List<ActorOutDTO> actors;
}
