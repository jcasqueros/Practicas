package com.pracs.films.persistence.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * Entity Serie
 *
 * @author Manuel Mateos de Torres
 */
@NoArgsConstructor
@Table(name = "series")
@Entity
public class Serie extends Production {

}
