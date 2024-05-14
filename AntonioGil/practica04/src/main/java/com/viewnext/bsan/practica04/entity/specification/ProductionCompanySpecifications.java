package com.viewnext.bsan.practica04.entity.specification;

import com.viewnext.bsan.practica04.entity.ProductionCompany;
import com.viewnext.bsan.practica04.entity.ProductionCompany_;
import org.springframework.data.jpa.domain.Specification;

public class ProductionCompanySpecifications {

    private ProductionCompanySpecifications() {
    }

    public static Specification<ProductionCompany> nameContainsIgnoreCase(String substring) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.upper(root.get(ProductionCompany_.name)), "%" + substring.toUpperCase() + "%"
        );
    }

    public static Specification<ProductionCompany> yearFoundedIsEqualTo(int year) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(ProductionCompany_.yearFounded), year);
    }

}
