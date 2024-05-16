package com.example.demo.exception;

import org.hibernate.service.spi.ServiceException;

public class EmptyException extends ServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1184005910556054933L;

	/**
	 * 
	 */
	public EmptyException(String mensaje) {
		super(mensaje);
	}

	
}
