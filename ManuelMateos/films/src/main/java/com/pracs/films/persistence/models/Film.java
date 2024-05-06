package com.pracs.films.persistence.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;

@Builder
@Table(name = "films")
@Entity
public class Film extends Production {

}
