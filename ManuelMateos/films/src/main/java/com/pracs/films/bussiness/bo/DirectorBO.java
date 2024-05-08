package com.pracs.films.bussiness.bo;

import com.pracs.films.persistence.models.Director;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bussines Object of {@link Director}
 *
 * @author Manuel Mateos de Torres
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectorBO {

    private long id;

    private String name;

    private int age;

    private String nationality;
}
