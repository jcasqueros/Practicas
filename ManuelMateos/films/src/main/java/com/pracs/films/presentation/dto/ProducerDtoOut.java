package com.pracs.films.presentation.dto;

import com.pracs.films.persistence.models.Producer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object of {@link Producer}
 *
 * @author Manuel Mateos de Torres
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProducerDtoOut {

    private long id;

    private String name;

    private int debut;
}
