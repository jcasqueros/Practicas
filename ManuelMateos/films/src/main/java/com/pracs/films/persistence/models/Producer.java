package com.pracs.films.persistence.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity Producer
 *
 * @author Manuel Mateos de Torres
 */
@Data
@NoArgsConstructor
@Table(name = "producers")
@Entity
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCER_SEQ")
    private long id;

    private String name;

    private int debut;
}
