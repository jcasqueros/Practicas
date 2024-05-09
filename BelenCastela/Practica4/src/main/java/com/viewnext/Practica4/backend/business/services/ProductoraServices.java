package com.viewnext.Practica4.backend.business.services;

import java.util.List;

import com.viewnext.Practica4.backend.business.bo.ProductoraBo;
import com.viewnext.Practica4.backend.business.model.Productora;

public interface ProductoraServices {

	//JPA
	ProductoraBo create(ProductoraBo productoraBo);
	ProductoraBo read(long id);
	List<ProductoraBo> getAll();
	ProductoraBo update(ProductoraBo productoraBo);
	public void delete(long id);

	//CRITERIA BUILDER
	ProductoraBo createCb(ProductoraBo productoraBo);
    ProductoraBo readCb(long id);
    List<ProductoraBo> getProductorasCb();
    ProductoraBo updateCb(ProductoraBo productoraBo);
    void deleteCb(long id);
}
