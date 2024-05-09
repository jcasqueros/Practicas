package com.viewnext.Practica4.backend.business.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viewnext.Practica4.backend.business.bo.DirectorBo;
import com.viewnext.Practica4.backend.business.model.Director;
import com.viewnext.Practica4.backend.business.services.DirectorServices;
import com.viewnext.Practica4.backend.repository.DirectorRepository;
import com.viewnext.Practica4.backend.repository.custom.DirectorCustomRepository;
import com.viewnext.Practica4.util.converter.bo.BoToEntity;
import com.viewnext.Practica4.util.converter.bo.EntityToBo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DirectorServicesImpl implements DirectorServices {

    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    DirectorCustomRepository directorCustomRepository;

    @Autowired
    EntityToBo entityToBo;

    @Autowired
    BoToEntity boToEntity;

    @Override
    public DirectorBo create(DirectorBo directorBo) {
        try {
            return entityToBo.directorToDirectorBo(directorRepository.save(boToEntity.directorBoToDirector(directorBo)));
        } catch (Exception e) {
            throw new ServiceException("Error al crear director", e);
        }
    }

    @Override
    public DirectorBo read(long id) {
        return entityToBo.directorToDirectorBo(directorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id no encontrado")));
    }

    @Override
    public List<DirectorBo> getAll() {
        try {
            List<Director> directorList = directorRepository.findAll();
            List<DirectorBo> directorBoList = new ArrayList<>();
            directorList.forEach((director) -> directorBoList.add(entityToBo.directorToDirectorBo(director)));
            return directorBoList;
        } catch (Exception e) {
            throw new ServiceException("Error al obtener lista de directores", e);
        }
    }

    @Override
    public void delete(long id) {
        if (!directorRepository.existsById(id)) {
            throw new EntityNotFoundException("Director no encontrado");
        }
        directorRepository.deleteById(id);
    }

    @Override
    public DirectorBo update(DirectorBo directorBo) {
        try {
            return entityToBo.directorToDirectorBo(directorRepository.save(boToEntity.directorBoToDirector(directorBo)));
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar director", e);
        }
    }

    //CRITERIA BUILDER

    public List<DirectorBo> getDirectoresCb() {
        try {
            List<Director> directors = directorCustomRepository.getDirectoresCb();
            return directors.stream().map(entityToBo::directorToDirectorBo)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Error al obtener lista de directores con Criteria Builder", e);
        }
    }

    @Override
    public DirectorBo createCb(DirectorBo directorBo) {
        try {
            Director director = boToEntity.directorBoToDirector(directorBo);
            return entityToBo.directorToDirectorBo(directorCustomRepository.createCb(director));
        } catch (Exception e) {
            throw new ServiceException("Error al crear director con Criteria Builder", e);
        }
    }

    @Override
    public DirectorBo readCb(long id) {
        try {
            Director director = directorCustomRepository.readCb(id);
            if (director == null) {
                throw new EntityNotFoundException("Id no encontrado");
            }
            return entityToBo.directorToDirectorBo(director);
        } catch (Exception e) {
            throw new ServiceException("Error al leer director con Criteria Builder", e);
        }
    }

    @Override
    public DirectorBo updateCb(DirectorBo directorBo) {
        try {
            Director director = boToEntity.directorBoToDirector(directorBo);
            return entityToBo.directorToDirectorBo(directorCustomRepository.updateCb(director));
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar director con Criteria Builder", e);
        }
    }

    @Override
    public void deleteCb(long id) {
        try {
            directorCustomRepository.deleteCb(id);
        } catch (Exception e) {
            throw new ServiceException("Error al eliminar director con Criteria Builder", e);
        }
    }
}
