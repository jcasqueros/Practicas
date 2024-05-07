package com.viewnext.films.persistencelayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
