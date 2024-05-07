package com.viewnext.films.persistencelayer.entity;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;

/**
 * Entity class representing the "ACTOR" table in the database. This class extends {@link Worker} and defines the
 * structure and properties of Actor entities.
 *
 * @author Franciosco Balonero Olivera
 * @see Worker
 */
@EqualsAndHashCode(callSuper = true)
@Entity
public class Actor extends Worker {
}
