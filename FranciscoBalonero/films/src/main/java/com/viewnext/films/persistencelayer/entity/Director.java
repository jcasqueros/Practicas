package com.viewnext.films.persistencelayer.entity;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;

/**
 * Entity class representing the "DIRECTOR" table in the database. This class extends {@link Worker} and defines the
 * structure and properties of Director entities.
 *
 * @author Franciosco Balonero Olivera
 * @see Worker
 */
@EqualsAndHashCode(callSuper = true)
@Entity
public class Director extends Worker {

}
