package com.viewnext.Practica4.backend.business.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viewnext.Practica4.backend.business.bo.ProductoraBo;
import com.viewnext.Practica4.backend.business.model.Productora;
import com.viewnext.Practica4.backend.business.services.ProductoraServices;
import com.viewnext.Practica4.backend.repository.ProductoraRepository;
import com.viewnext.Practica4.backend.repository.custom.ProductoraCustomRepository;
import com.viewnext.Practica4.util.converter.bo.BoToEntity;
import com.viewnext.Practica4.util.converter.bo.EntityToBo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductoraServicesImpl implements ProductoraServices {

    @Autowired
    ProductoraRepository productoraRepository;

    @Autowired
    ProductoraCustomRepository productoraCustomRepository;

    @Autowired
    EntityToBo entityToBo;

    @Autowired
    BoToEntity boToEntity;

    @Override
    public ProductoraBo create(ProductoraBo productoraBo) {
        try {
            return entityToBo.productoraToProductoraBo(productoraRepository.save(boToEntity.productoraBoToProductora(productoraBo)));
        } catch (Exception e) {
            throw new ServiceException("Error al crear productora", e);
        }
    }

    @Override
    public ProductoraBo read(long id) {
        return entityToBo.productoraToProductoraBo(productoraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id no encontrado")));
    }

    @Override
    public List<ProductoraBo> getAll() {
        try {
            List<Productora> productoraList = productoraRepository.findAll();
            List<ProductoraBo> productoraBoList = new ArrayList<>();
            productoraList.forEach((productora) -> productoraBoList.add(entityToBo.productoraToProductoraBo(productora)));
            return productoraBoList;
        } catch (Exception e) {
            throw new ServiceException("Error al obtener lista de productoras", e);
        }
    }

    @Override
    public void delete(long id) {
        if (!productoraRepository.existsById(id)) {
            throw new EntityNotFoundException("Productora no encontrada");
        }
        productoraRepository.deleteById(id);
    }

    @Override
    public ProductoraBo update(ProductoraBo productoraBo) {
        try {
            return entityToBo.productoraToProductoraBo(productoraRepository.save(boToEntity.productoraBoToProductora(productoraBo)));
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar productora", e);
        }
    }

    //CRITERIA BUILDER

    public List<ProductoraBo> getProductorasCb() {
        try {
            List<Productora> productoras = productoraCustomRepository.getProductorasCb();
            return productoras.stream().map(entityToBo::productoraToProductoraBo)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Error al obtener lista de productoras con Criteria Builder", e);
        }
    }

    @Override
    public ProductoraBo createCb(ProductoraBo productoraBo) {
        try {
            Productora productora = boToEntity.productoraBoToProductora(productoraBo);
            return entityToBo.productoraToProductoraBo(productoraCustomRepository.createCb(productora));
        } catch (Exception e) {
            throw new ServiceException("Error al crear productora con Criteria Builder", e);
        }
    }

    @Override
    public ProductoraBo readCb(long id) {
        try {
            Productora productora = productoraCustomRepository.readCb(id);
            if (productora == null) {
                throw new EntityNotFoundException("Id no encontrado");
            }
            return entityToBo.productoraToProductoraBo(productora);
        } catch (Exception e) {
            throw new ServiceException("Error al leer productora con Criteria Builder", e);
        }
    }

    @Override
    public ProductoraBo updateCb(ProductoraBo productoraBo) {
        try {
            Productora productora = boToEntity.productoraBoToProductora(productoraBo);
            return entityToBo.productoraToProductoraBo(productoraCustomRepository.updateCb(productora));
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar productora con Criteria Builder", e);
        }
    }

    @Override
    public void deleteCb(long id) {
        try {
            productoraCustomRepository.deleteCb(id);
        } catch (Exception e) {
            throw new ServiceException("Error al eliminar productora con Criteria Builder", e);
        }
    }
}
