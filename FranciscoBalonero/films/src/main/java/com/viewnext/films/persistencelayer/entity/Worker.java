package com.viewnext.films.persistencelayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class that represents the "WORKER" table. It is an abstract class that is used for classes to inherit
 * {@link Actor} and {@link Director}.
 *
 * @author Franciosco Balonero Olivera
 * @see Actor
 * @see Director
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WORKER_SEQ")
    private Long id;

    private String name;

    private int age;

    private String nationality;
}
