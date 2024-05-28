package com.viewnext.bsan.practica03.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

/**
 * The {@code User} entity class represents the users registered in the system.
 *
 * @author Antonio Gil
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "M_USER")
public class User {

    @Id
    @Column(name = "M_DNI")
    String dni;

    @Column(name = "M_NAME")
    String name;

    @Column(name = "M_SURNAME")
    String surname;

    @Column(name = "M_AGE")
    int age;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(dni, user.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }
}