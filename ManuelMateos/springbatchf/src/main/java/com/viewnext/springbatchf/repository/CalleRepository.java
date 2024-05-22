package com.viewnext.springbatchf.repository;

import com.viewnext.springbatchf.model.Calle;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalleRepository extends MongoRepository<Calle, ObjectId> {
}
