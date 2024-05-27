package com.viewnext.bsan.practica04.persistence.specification;

import com.viewnext.bsan.practica04.persistence.entity.Show;
import com.viewnext.bsan.practica04.persistence.entity.Show_;
import org.springframework.data.jpa.domain.Specification;

public class ShowSpecifications {

    private ShowSpecifications() {
    }

    public static Specification<Show> titleContainsIgnoreCase(String substring) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.upper(root.get(Show_.title)), "%" + substring.toUpperCase() + "%"
        );
    }

    public static Specification<Show> yearIsEqualTo(int year) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Show_.year), year);
    }

}
