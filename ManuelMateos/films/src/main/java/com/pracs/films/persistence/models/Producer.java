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
@Table(name = "producers")
@Entity
public class Producer {

    @Id
    @Column(name = "producer_id")
    private long id;

    @Column(name = "producer_name")
    private String name;

    @Column(name = "producer_year")
    private int year;
}
