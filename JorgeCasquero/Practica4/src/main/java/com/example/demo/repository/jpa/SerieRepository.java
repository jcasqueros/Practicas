package com.example.demo.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Serie;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long>{

}
