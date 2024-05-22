package com.viewnext.srpingbatchchf.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.viewnext.srpingbatchchf.model.TramoCalle;

@Repository
public interface TramoCalleDAO extends CrudRepository<TramoCalle, Long>{

}
