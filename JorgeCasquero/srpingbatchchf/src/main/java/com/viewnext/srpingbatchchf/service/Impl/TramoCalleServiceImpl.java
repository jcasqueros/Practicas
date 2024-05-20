package com.viewnext.srpingbatchchf.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.viewnext.srpingbatchchf.model.TramoCalle;
import com.viewnext.srpingbatchchf.persistence.TramoCalleDAO;
import com.viewnext.srpingbatchchf.service.TramoCalleService;

public class TramoCalleServiceImpl implements TramoCalleService {
	@Autowired
	private TramoCalleDAO tramoCalleDAO;

	@Override
	@Transactional
	public void saveAll(List<TramoCalle> tramo) {
		tramoCalleDAO.saveAll(tramo);

	}
}
