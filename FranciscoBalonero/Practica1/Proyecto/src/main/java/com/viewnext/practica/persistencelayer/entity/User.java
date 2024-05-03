package com.viewnext.practica.persistencelayer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing the "users" table in the database. This class defines the structure and properties of user
 * entities.
 *
 * @author Franciosco Balonero Olivera
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    /**
     * Entity fields
     */
    @Id
    private String dni;

    private String name;

    private String surname;

    private int age;
}
