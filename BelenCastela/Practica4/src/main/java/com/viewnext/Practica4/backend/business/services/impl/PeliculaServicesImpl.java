package com.viewnext.Practica4.backend.business.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viewnext.Practica4.backend.business.bo.PeliculaBo;
import com.viewnext.Practica4.backend.business.model.Pelicula;
import com.viewnext.Practica4.backend.business.services.PeliculaServices;
import com.viewnext.Practica4.backend.repository.PeliculaRepository;
import com.viewnext.Practica4.backend.repository.custom.PeliculaCustomRepository;
import com.viewnext.Practica4.util.converter.bo.BoToEntity;
import com.viewnext.Practica4.util.converter.bo.EntityToBo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PeliculaServicesImpl implements PeliculaServices {

    @Autowired
    PeliculaRepository peliculaRepository;

    @Autowired
    PeliculaCustomRepository peliculaCustomRepository;

    @Autowired
    EntityToBo entityToBo;

    @Autowired
    BoToEntity boToEntity;

    @Override
    public PeliculaBo create(PeliculaBo peliculaBo) {
        try {
            return entityToBo.peliculaToPeliculaBo(peliculaRepository.save(boToEntity.peliculaBoToPelicula(peliculaBo)));
        } catch (Exception e) {
            throw new ServiceException("Error al crear pelicula", e);
        }
    }

    @Override
    public PeliculaBo read(long id) {
        return entityToBo.peliculaToPeliculaBo(peliculaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id no encontrado")));
    }

    @Override
    public List<PeliculaBo> getAll() {
        try {
            List<Pelicula> peliculaList = peliculaRepository.findAll();
            List<PeliculaBo> peliculaBoList = new ArrayList<>();
            peliculaList.forEach((pelicula) -> peliculaBoList.add(entityToBo.peliculaToPeliculaBo(pelicula)));
            return peliculaBoList;
        } catch (Exception e) {
            throw new ServiceException("Error al obtener lista de peliculas", e);
        }
    }

    @Override
    public void delete(long id) {
        if (!peliculaRepository.existsById(id)) {
            throw new EntityNotFoundException("Pelicula no encontrada");
        }
        peliculaRepository.deleteById(id);
    }

    @Override
    public PeliculaBo update(PeliculaBo peliculaBo) {
        try {
            return entityToBo.peliculaToPeliculaBo(peliculaRepository.save(boToEntity.peliculaBoToPelicula(peliculaBo)));
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar pelicula", e);
        }
    }

    //CRITERIA BUILDER

    public List<PeliculaBo> getPeliculasCb() {
        try {
            List<Pelicula> peliculas = peliculaCustomRepository.getPeliculasCb();
            return peliculas.stream().map(entityToBo::peliculaToPeliculaBo)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Error al obtener lista de peliculas con Criteria Builder", e);
        }
    }

    @Override
    public PeliculaBo createCb(PeliculaBo peliculaBo) {
        try {
            Pelicula pelicula = boToEntity.peliculaBoToPelicula(peliculaBo);
            return entityToBo.peliculaToPeliculaBo(peliculaCustomRepository.createCb(pelicula));
        } catch (Exception e) {
            throw new ServiceException("Error al crear pelicula con Criteria Builder", e);
        }
    }

    @Override
    public PeliculaBo readCb(long id) {
        try {
            Pelicula pelicula = peliculaCustomRepository.readCb(id);
            if (pelicula == null) {
                throw new EntityNotFoundException("Id no encontrado");
            }
            return entityToBo.peliculaToPeliculaBo(pelicula);
        } catch (Exception e) {
            throw new ServiceException("Error al leer pelicula con Criteria Builder", e);
        }
    }

    @Override
    public PeliculaBo updateCb(PeliculaBo peliculaBo) {
        try {
            Pelicula pelicula = boToEntity.peliculaBoToPelicula(peliculaBo);
            return entityToBo.peliculaToPeliculaBo(peliculaCustomRepository.updateCb(pelicula));
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar pelicula con Criteria Builder", e);
        }
    }

    @Override
    public void deleteCb(long id) {
        try {
            peliculaCustomRepository.deleteCb(id);
        } catch (Exception e) {
            throw new ServiceException("Error al eliminar pelicula con Criteria Builder", e);
        }
    }
}
