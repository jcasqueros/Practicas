package com.viewnext.srpingbatchchf.step.chunk;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.viewnext.srpingbatchchf.model.TramoCalle;

public class TramoCalleItemProcessor implements ItemProcessor<TramoCalle, TramoCalle> {
	private Validator<TramoCalle> validator;

	@Autowired
	private MongoTemplate mongoTemplate;

	public TramoCalleItemProcessor(Validator<TramoCalle> validator) {
		this.validator = validator;
	}

	@Override
	public TramoCalle process(final TramoCalle tramoCalle) throws Exception {
		validator.validate(tramoCalle);
		Query query = new Query();
        query.addCriteria(Criteria.where("codigoCalle").is(tramoCalle.getCodigoCalle()));
        if (mongoTemplate.exists(query, TramoCalle.class)) {
            return null;
        }
        
		return tramoCalle;
	}
}
