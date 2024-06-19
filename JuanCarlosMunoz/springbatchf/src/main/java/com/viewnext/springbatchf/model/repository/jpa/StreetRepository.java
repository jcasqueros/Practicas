package com.viewnext.springbatchf.model.repository.jpa;

import com.viewnext.springbatchf.model.entity.Street;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Street repository.
 */
@Repository
public interface StreetRepository extends MongoRepository<Street, ObjectId> {
}
