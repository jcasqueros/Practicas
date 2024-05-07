package com.pracs.films.persistence.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;

/**
 * Entity Director
 *
 * @author Manuel Mateos de Torres
 */
@Builder
@Table(name = "directors")
@Entity
public class Director extends Person {

}
