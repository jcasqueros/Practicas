package com.viewnext.films.persistencelayer.entity;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;

/**
 * Entity class representing the "SERIE" table in the database. This class extends {@link Production} and defines the
 * structure and properties of Serie entities.
 *
 * @author Franciosco Balonero Olivera
 * @see Production
 */
@EqualsAndHashCode(callSuper = true)
@Entity
public class Serie extends Production {
}
