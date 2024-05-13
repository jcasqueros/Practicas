package com.viewnext.films.presentationlayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO representing Serie to update information.
 *
 * @author Franciosco Balonero Olivera
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing Serie to update information")
public class SerieUpdateDTO {

    @Schema(description = "Id of the serie", example = "1")
    @NotNull
    private long id;

    @Schema(description = "Title of the serie", example = "Friends")
    @NotBlank
    private String title;

    @Schema(description = "Release year of the serie", example = "2006")
    @NotNull
    @Positive
    private int releaseYear;

    @Schema(description = "Director of the serie")
    private DirectorOutDTO director;

    @Schema(description = "Producer of the serie")
    private ProducerOutDTO producer;

    @Schema(description = "Actors of the serie")
    private List<ActorOutDTO> actors;
}
