package com.viewnext.bsan.practica04.entity.specification;

import com.viewnext.bsan.practica04.entity.Director;
import com.viewnext.bsan.practica04.entity.Director_;
import org.springframework.data.jpa.domain.Specification;

public class DirectorSpecifications {

    private DirectorSpecifications() {
    }

    public static Specification<Director> nameContains(String substring) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.upper(root.get(Director_.name)), "%" + substring.toUpperCase() + "%"
        ));
    }

    public static Specification<Director> ageIsEqualTo(int age) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Director_.age), age));
    }

    public static Specification<Director> nationalityIsEqualToIgnoreCase(String nationality) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(
                criteriaBuilder.upper(root.get(Director_.nationality)), nationality.toUpperCase()
        ));
    }

}
