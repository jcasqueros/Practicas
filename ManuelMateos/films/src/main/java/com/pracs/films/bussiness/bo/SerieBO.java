package com.pracs.films.bussiness.bo;

import com.pracs.films.persistence.models.Serie;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Bussines Object of {@link Serie}
 *
 * @author Manuel Mateos de Torres
 */
@Data
@NoArgsConstructor
public class SerieBO {

    private long id;

    private String title;

    private int debut;

    private DirectorBO director;

    private ProducerBO producer;

    private List<ActorBO> actors;
}
