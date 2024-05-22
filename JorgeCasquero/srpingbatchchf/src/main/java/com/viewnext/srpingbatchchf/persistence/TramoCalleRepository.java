package com.viewnext.srpingbatchchf.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viewnext.srpingbatchchf.model.TramoCalle;


@Repository
public interface TramoCalleRepository extends JpaRepository<TramoCalle, Long> {

}