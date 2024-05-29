package com.viewnext.springbatch.step.chunk;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import com.viewnext.springbatch.model.Direccion;

public class DireccionItemProcess implements ItemProcessor<Direccion, Direccion>{

	//Starting parameter changed in Run/Run Configurations/Arguments for 'CENTRO'
	@Value("${nom.distrito:CIUDAD JARDIN}")
	String nomDistrito;

	@Override
	public Direccion process(Direccion item) throws Exception {
		if (nomDistrito.equals(item.getNomDistrito())) {
			return item;
		} else {
			return null;
		}
	}
	
}
