package com.pracs.films.persistence.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;

/**
 * Entity Serie
 *
 * @author Manuel Mateos de Torres
 */
@Builder
@Table(name = "series")
@Entity
public class Serie extends Production {

}
