package com.viewnext.films.businesslayer.service;

import com.viewnext.films.businesslayer.exception.NotFoundException;

public interface WebClientService {
    
    void existsActor(long id) throws NotFoundException;

    void existsDirector(long id) throws NotFoundException;

    void existsProducer(long id) throws NotFoundException;
}
