package com.pracs.films.persistence.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity Person
 *
 * @author Manuel Mateos de Torres
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {

    @Id
    @SequenceGenerator(name = "person_seq")
    private long id;

    private String name;

    private int age;

    private String nationality;
}
