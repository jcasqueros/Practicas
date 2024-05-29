package com.viewnext.bsan.practica04.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

/**
 * The {@code Actor} entity class represents the actors registered in the system at the persistence layer level.
 *
 * @author Antonio Gil
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ACTORS")
public class Actor {

    @Id
    @Column(name = "ACTOR_ID")
    Long id;

    @Column(name = "ACTOR_NAME")
    String name;

    @Column(name = "ACTOR_AGE")
    int age;

    @Column(name = "ACTOR_NATIONALITY")
    String nationality;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(id, actor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}