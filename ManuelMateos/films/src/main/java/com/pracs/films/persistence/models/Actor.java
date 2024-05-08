package com.pracs.films.persistence.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

/**
 * Entity Actor
 *
 * @author Manuel Mateos de Torres
 */
@EqualsAndHashCode
@Table(name = "actors")
@Entity
public class Actor extends Person {

}
