package com.pracs.films.presentation.dto;

import com.pracs.films.persistence.models.Film;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object of {@link Film}
 *
 * @author Manuel Mateos de Torres
 */
@Data
@NoArgsConstructor
public class FilmDtoOut {

    private long id;

    private String title;

    private int debut;

    private DirectorDtoOut director;

    private ProducerDtoOut producer;

    private List<ActorDtoOut> actors;
}
