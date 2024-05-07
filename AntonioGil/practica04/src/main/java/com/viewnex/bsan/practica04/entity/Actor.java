package com.viewnex.bsan.practica04.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

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
    short age;

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
