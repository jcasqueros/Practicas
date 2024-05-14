package com.viewnext.bsan.practica04.entity.specification;

import com.viewnext.bsan.practica04.entity.Actor;
import com.viewnext.bsan.practica04.entity.Actor_;
import org.springframework.data.jpa.domain.Specification;

public class ActorSpecifications {

    private ActorSpecifications() {
    }

    public static Specification<Actor> nameContainsIgnoreCase(String substring) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.upper(root.get(Actor_.name)),"%" + substring.toUpperCase() + "%"
        );
    }

    public static Specification<Actor> ageIsEqualTo(int age) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Actor_.age), age);
    }

    public static Specification<Actor> nationalityIsEqualToIgnoreCase(String nationality) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                criteriaBuilder.upper(root.get(Actor_.nationality)), nationality.toUpperCase()
        );
    }

}
