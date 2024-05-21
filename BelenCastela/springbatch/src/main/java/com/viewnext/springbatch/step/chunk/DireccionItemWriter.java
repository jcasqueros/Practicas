package com.viewnext.springbatch.step.chunk;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.viewnext.springbatch.model.Direccion;
import com.viewnext.springbatch.service.DireccionService;

import java.util.ArrayList;
import java.util.List;

public class DireccionItemWriter implements ItemWriter<Direccion>{
	
	@Autowired
	private DireccionService direccionService;

	@Override
	public void write(Chunk<? extends Direccion> list) throws Exception {
		List<Direccion> direccionList = new ArrayList<Direccion>();
		for (Direccion direccion : list) {
			direccionList.add(direccion);
		}
		direccionService.saveAll(direccionList);
		}

}
