package com.pracs.films.presentation.dto;

import com.pracs.films.persistence.models.Actor;
import com.pracs.films.persistence.models.Director;
import com.pracs.films.persistence.models.Film;
import com.pracs.films.persistence.models.Producer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object of {@link Film}
 *
 * @author Manuel Mateos de Torres
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmDtoOut {

    private long id;

    private String title;

    private int debut;

    private Director director;

    private Producer producer;

    private List<Actor> actors;
}
