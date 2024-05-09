package com.viewnext.films.presentationlayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO representing Film to update information.
 *
 * @author Franciosco Balonero Olivera
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing Film to update information")
public class FilmUpdateDTO {

    @Schema(description = "Id of the film", example = "1")
    @NotNull
    private long id;

    @Schema(description = "Title of the film", example = "Friends")
    @NotBlank
    private String title;

    @Schema(description = "Release year of the film", example = "2006")
    @NotNull
    private int releaseYear;

    @Schema(description = "Director of the film")
    private DirectorOutDTO director;

    @Schema(description = "Producer of the film")
    private ProducerOutDTO producer;

    @Schema(description = "Actors of the film")
    private List<ActorOutDTO> actors;
}
