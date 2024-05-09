package com.pracs.films.bussiness.bo;

import com.pracs.films.persistence.models.Film;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Bussines Object of {@link Film}
 *
 * @author Manuel Mateos de Torres
 */
@Data
@NoArgsConstructor
public class FilmBO {

    private long id;

    private String title;

    private int debut;

    private DirectorBO director;

    private ProducerBO producer;

    private List<ActorBO> actors;
}
