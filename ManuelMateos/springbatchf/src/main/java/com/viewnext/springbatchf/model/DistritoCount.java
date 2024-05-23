package com.viewnext.springbatchf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a distrito count entity in database
 *
 * @author Manuel Mateos de Torres
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "distrito_count")
public class DistritoCount {

    private String nomDistrito;
    private long count;
}
