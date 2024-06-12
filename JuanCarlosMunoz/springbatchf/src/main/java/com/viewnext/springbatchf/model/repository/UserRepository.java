package com.viewnext.springbatchf.model.repository;

import com.viewnext.springbatchf.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface District repository.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
