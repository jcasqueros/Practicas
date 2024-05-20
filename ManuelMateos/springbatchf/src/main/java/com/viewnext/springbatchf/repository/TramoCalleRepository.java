package com.viewnext.springbatchf.repository;

import com.viewnext.springbatchf.model.TramoCalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TramoCalleRepository extends JpaRepository<TramoCalle, Long> {
}
