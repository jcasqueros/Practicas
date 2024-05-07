package com.pracs.films.persistence.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity Producer
 *
 * @author Manuel Mateos de Torres
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "producers")
@Entity
public class Producer {

    @Id
    @SequenceGenerator(name = "producer_seq")
    private long id;

    private String name;

    private int debut;
}
