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

    @Override
    public void save(Distrito distrito) {
        distritoDAO.save(distrito);
    }

    @Override
    public Iterable<Distrito> findAll() {
        return distritoDAO.findAll();
    }
}