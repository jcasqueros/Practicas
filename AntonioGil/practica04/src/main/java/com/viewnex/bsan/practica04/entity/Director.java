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
@Table(name = "DIRECTORS")
public class Director {

    @Id
    @Column(name = "DIRECTOR_ID")
    Long id;

    @Column(name = "DIRECTOR_NAME")
    String name;

    @Column(name = "DIRECTOR_AGE")
    short age;

    @Column(name = "DIRECCTOR_NATIONALITY")
    String nationality;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Director actor = (Director) o;
        return Objects.equals(id, actor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
