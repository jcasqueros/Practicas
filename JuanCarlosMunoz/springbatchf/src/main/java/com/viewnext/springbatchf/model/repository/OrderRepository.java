package com.viewnext.springbatchf.model.repository;

import com.viewnext.springbatchf.model.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface District repository.
 */
@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {
}
