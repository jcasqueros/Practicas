package com.viewnext.springbatch.repository;

import com.viewnext.springbatch.model.Calle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalleRepository extends JpaRepository<Calle, Long> {

}
