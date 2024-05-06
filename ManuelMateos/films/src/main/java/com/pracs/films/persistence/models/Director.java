package com.pracs.films.persistence.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "directors")
@Entity
public class Director {

    @Id
    @Column(name = "director_id")
    private long id;

    @Column(name = "director_name")
    private String name;

    @Column(name = "director_age")
    private int age;

    @Column(name = "director_nacionality")
    private String nacionality;
}
