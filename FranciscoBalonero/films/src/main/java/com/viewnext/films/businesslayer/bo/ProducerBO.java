package com.viewnext.films.businesslayer.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Producer business object.
 *
 * @author Francisco Balonero Olivera
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerBO {

    private long id;

    private String name;

    private int foundationYear;
}
