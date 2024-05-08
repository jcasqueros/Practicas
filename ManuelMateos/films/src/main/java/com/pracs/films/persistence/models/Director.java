package com.pracs.films.persistence.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * Entity Director
 *
 * @author Manuel Mateos de Torres
 */
@NoArgsConstructor
@Table(name = "directors")
@Entity
public class Director extends Person {

}
