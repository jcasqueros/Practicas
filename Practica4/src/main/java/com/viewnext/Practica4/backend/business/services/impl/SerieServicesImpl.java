package com.viewnext.Practica4.backend.business.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viewnext.Practica4.backend.business.bo.SerieBo;
import com.viewnext.Practica4.backend.business.model.Serie;
import com.viewnext.Practica4.backend.business.services.SerieServices;
import com.viewnext.Practica4.backend.repository.SerieRepository;
import com.viewnext.Practica4.backend.repository.custom.SerieCustomRepository;
import com.viewnext.Practica4.util.converter.bo.BoToEntity;
import com.viewnext.Practica4.util.converter.bo.EntityToBo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SerieServicesImpl implements SerieServices {

    @Autowired
    SerieRepository serieRepository;

    @Autowired
    SerieCustomRepository serieCustomRepository;

    @Autowired
    EntityToBo entityToBo;

    @Autowired
    BoToEntity boToEntity;

    @Override
    public SerieBo create(SerieBo serieBo) {
        try {
            return entityToBo.serieToSerieBo(serieRepository.save(boToEntity.serieBoToSerie(serieBo)));
        } catch (Exception e) {
            throw new ServiceException("Error al crear serie", e);
        }
    }

    @Override
    public SerieBo read(long id) {
        return entityToBo.serieToSerieBo(serieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id no encontrado")));
    }

    @Override
    public List<SerieBo> getAll() {
        try {
            List<Serie> serieList = serieRepository.findAll();
            List<SerieBo> serieBoList = new ArrayList<>();
            serieList.forEach((serie) -> serieBoList.add(entityToBo.serieToSerieBo(serie)));
            return serieBoList;
        } catch (Exception e) {
            throw new ServiceException("Error al obtener lista de series", e);
        }
    }

    @Override
    public void delete(long id) {
        if (!serieRepository.existsById(id)) {
            throw new EntityNotFoundException("Serie no encontrada");
        }
        serieRepository.deleteById(id);
    }

    @Override
    public SerieBo update(SerieBo serieBo) {
        try {
            return entityToBo.serieToSerieBo(serieRepository.save(boToEntity.serieBoToSerie(serieBo)));
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar serie", e);
        }
    }

    //CRITERIA BUILDER

    public List<SerieBo> getSeriesCb() {
        try {
            List<Serie> series = serieCustomRepository.getSeriesCb();
            return series.stream().map(entityToBo::serieToSerieBo)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Error al obtener lista de series con Criteria Builder", e);
        }
    }

    @Override
    public SerieBo createCb(SerieBo serieBo) {
        try {
            Serie serie = boToEntity.serieBoToSerie(serieBo);
            return entityToBo.serieToSerieBo(serieCustomRepository.createCb(serie));
        } catch (Exception e) {
            throw new ServiceException("Error al crear serie con Criteria Builder", e);
        }
    }

    @Override
    public SerieBo readCb(long id) {
        try {
            Serie serie = serieCustomRepository.readCb(id);
            if (serie == null) {
                throw new EntityNotFoundException("Id no encontrado");
            }
            return entityToBo.serieToSerieBo(serie);
        } catch (Exception e) {
            throw new ServiceException("Error al leer serie con Criteria Builder", e);
        }
    }

    @Override
    public SerieBo updateCb(SerieBo serieBo) {
        try {
            Serie serie = boToEntity.serieBoToSerie(serieBo);
            return entityToBo.serieToSerieBo(serieCustomRepository.updateCb(serie));
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar serie con Criteria Builder", e);
        }
    }

    @Override
    public void deleteCb(long id) {
        try {
            serieCustomRepository.deleteCb(id);
        } catch (Exception e) {
            throw new ServiceException("Error al eliminar serie con Criteria Builder", e);
        }
    }
}
