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
@Table(name = "PRODUCTION_COMPANIES")
public class ProductionCompany {

    @Id
    @Column(name = "PRODUCTION_COMPANY_ID")
    Long id;

    @Column(name = "PRODUCTION_COMPANY_NAME")
    String name;

    @Column(name = "PRODUCTION_COMPANY_YEAR_FOUNDED")
    int yearFounded;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionCompany that = (ProductionCompany) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
