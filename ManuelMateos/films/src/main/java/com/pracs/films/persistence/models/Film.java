package com.pracs.films.persistence.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;

/**
 * Entity Film
 *
 * @author Manuel Mateos de Torres
 */
@Builder
@Table(name = "films")
@Entity
public class Film extends Production {

}
