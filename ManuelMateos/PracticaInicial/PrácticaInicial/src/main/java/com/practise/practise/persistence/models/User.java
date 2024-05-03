package com.practise.practise.persistence.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity User
 *
 * @author Manuel Mateos de Torres
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
@Builder
@Entity
public class User {

    @Id
    private String dni;

    private String name;

    private String surname;

    private int age;
}
