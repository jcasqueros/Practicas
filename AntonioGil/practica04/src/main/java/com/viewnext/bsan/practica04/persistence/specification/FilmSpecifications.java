package com.viewnext.bsan.practica04.persistence.specification;

import com.viewnext.bsan.practica04.persistence.entity.Film;
import com.viewnext.bsan.practica04.persistence.entity.Film_;
import org.springframework.data.jpa.domain.Specification;

public class FilmSpecifications {

    private FilmSpecifications() {
    }

    public static Specification<Film> titleContainsIgnoreCase(String substring) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.upper(root.get(Film_.title)), "%" + substring.toUpperCase() + "%"
        );
    }

    public static Specification<Film> yearIsEqualTo(int year) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Film_.year), year);
    }

}
