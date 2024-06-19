package com.viewnext.springbatchf.model.repository.jpa;

import com.viewnext.springbatchf.model.entity.District;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface District repository.
 */
@Repository
public interface DistrictRepository extends MongoRepository<District, ObjectId> {
}
