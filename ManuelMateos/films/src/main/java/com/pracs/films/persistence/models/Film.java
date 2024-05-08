package com.pracs.films.persistence.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * Entity Film
 *
 * @author Manuel Mateos de Torres
 */
@NoArgsConstructor
@Table(name = "films")
@Entity
public class Film extends Production {

}
