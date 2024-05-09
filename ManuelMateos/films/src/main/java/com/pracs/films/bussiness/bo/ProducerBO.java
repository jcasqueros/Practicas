package com.pracs.films.bussiness.bo;

import com.pracs.films.persistence.models.Producer;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bussines Object of {@link Producer}
 *
 * @author Manuel Mateos de Torres
 */
@Data
@NoArgsConstructor
public class ProducerBO {

    private long id;

    private String name;

    private int debut;
}
