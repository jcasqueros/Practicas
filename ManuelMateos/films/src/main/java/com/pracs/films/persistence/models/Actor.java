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
@Table(name = "actors")
@Entity
public class Actor {

    @Id
    @Column(name = "actor_id")
    private long id;

    @Column(name = "actor_name")
    private String name;

    @Column(name = "actor_age")
    private int age;

    @Column(name = "actor_nacionality")
    private String nacionality;
}
