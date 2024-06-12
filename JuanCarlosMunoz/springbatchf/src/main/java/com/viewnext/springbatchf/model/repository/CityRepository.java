package com.viewnext.springbatchf.model.repository;

import com.viewnext.springbatchf.model.entity.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface District repository.
 */
@Repository
public interface CityRepository extends MongoRepository<City, Integer> {
}
