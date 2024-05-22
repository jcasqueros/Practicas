package com.viewnext.springbatch.repository;

import com.viewnext.springbatch.model.Calle;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Calle entities.
 *
 * @author Francisco Balonero Olivera
 */
@Repository
public interface CalleRepository extends MongoRepository<Calle, ObjectId> {

}
