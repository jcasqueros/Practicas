package com.viewnext.springbatch.step.chunk;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.ItemProcessor;

import com.viewnext.springbatch.model.Direccion;

public class DireccionItemProcess implements ItemProcessor<Direccion, Direccion>{

	@Override
	public Direccion process(Direccion item) throws Exception {
		
		/*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime date = LocalDateTime.now();*/
		
		return null;
	}

}
