package com.pracs.films.persistence.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;

@Builder
@Table(name = "actors")
@Entity
public class Actor extends Person {

}
