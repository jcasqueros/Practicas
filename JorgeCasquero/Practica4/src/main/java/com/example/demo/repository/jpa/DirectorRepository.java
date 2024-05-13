package com.example.demo.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Director;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
	public Page<Director> findAll(Pageable pageable);
}
