package com.pracs.films.bussiness.bo;

import com.pracs.films.persistence.models.Director;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bussines Object of {@link Director}
 *
 * @author Manuel Mateos de Torres
 */
@Data
@NoArgsConstructor
public class DirectorBO {

    private long id;

    private String name;

    private int age;

    private String nationality;
}
