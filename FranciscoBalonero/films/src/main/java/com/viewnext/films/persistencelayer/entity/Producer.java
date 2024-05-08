package com.viewnext.films.persistencelayer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing the "PRODUCER" table in the database. This class defines the structure and properties of
 * Producer entities.
 *
 * @author Franciosco Balonero Olivera
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCER_SEQ")
    private long id;

    @NotBlank
    private String name;

    @NotNull
    private int foundationYear;
}
