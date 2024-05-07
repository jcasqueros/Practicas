package com.viewnex.bsan.practica04.repository;

import com.viewnex.bsan.practica04.entity.ProductionCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionCompanyRepository extends JpaRepository<ProductionCompany, Long> {

}
