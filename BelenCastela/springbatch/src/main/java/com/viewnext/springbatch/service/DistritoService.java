package com.viewnext.springbatch.service;

import java.util.List;

import com.viewnext.springbatch.model.Distrito;

public interface DistritoService {
    Iterable<Distrito> saveAll(List<Distrito> distritos);
}
