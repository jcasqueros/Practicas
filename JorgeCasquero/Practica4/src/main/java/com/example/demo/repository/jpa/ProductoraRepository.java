package com.example.demo.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Productora;


@Repository
public interface ProductoraRepository extends JpaRepository<Productora, Long>{

}
