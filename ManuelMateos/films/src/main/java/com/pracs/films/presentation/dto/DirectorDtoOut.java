package com.pracs.films.presentation.dto;

import com.pracs.films.persistence.models.Director;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object of {@link Director}
 *
 * @author Manuel Mateos de Torres
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DirectorDtoOut {

    private long id;

    private String name;

    private int age;

    private String nationality;
}
