package com.pracs.films.presentation.dto;

import com.pracs.films.persistence.models.Serie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object of {@link Serie}
 *
 * @author Manuel Mateos de Torres
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SerieDtoOut {

    private long id;

    private String title;

    private int debut;

    private DirectorDtoOut director;

    private ProducerDtoOut producer;

    private List<ActorDtoOut> actors;
}
