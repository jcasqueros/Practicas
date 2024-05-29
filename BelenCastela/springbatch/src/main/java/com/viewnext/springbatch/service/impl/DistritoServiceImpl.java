package com.viewnext.springbatch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viewnext.springbatch.model.Distrito;
import com.viewnext.springbatch.repository.DistritoDAO;
import com.viewnext.springbatch.service.DistritoService;

@Service
public class DistritoServiceImpl implements DistritoService{

	@Autowired
    private DistritoDAO distritoDAO;

	public Iterable<Distrito> saveAll(List<Distrito> distritos) {
        return distritoDAO.saveAll(distritos);
    }
}