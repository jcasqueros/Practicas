package com.viewnext.springbatch.service;

import java.util.List;

import com.viewnext.springbatch.model.Distrito;

public interface DistritoService {
    void save(Distrito distrito);
    Iterable<Distrito> findAll();
}
