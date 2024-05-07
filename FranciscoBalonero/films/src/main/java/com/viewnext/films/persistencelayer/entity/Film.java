package com.viewnext.films.persistencelayer.entity;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;

/**
 * Entity class representing the "FILM" table in the database. This class extends {@link Production} and defines the
 * structure and properties of Film entities.
 *
 * @author Franciosco Balonero Olivera
 * @see Production
 */
@EqualsAndHashCode(callSuper = true)
@Entity
public class Film extends Production {
}
