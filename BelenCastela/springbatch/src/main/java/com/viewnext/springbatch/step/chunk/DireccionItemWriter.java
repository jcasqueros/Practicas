package com.viewnext.springbatch.step.chunk;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.viewnext.springbatch.model.Direccion;
import com.viewnext.springbatch.repository.DireccionDAO;
import com.viewnext.springbatch.service.DireccionService;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class DireccionItemWriter implements ItemWriter<Direccion>{

	@Autowired
	private DireccionService direccionService;

	@Override
	public void write(Chunk<? extends Direccion> chunk) throws Exception {
		for (Direccion direccion : chunk) {
			direccionService.save(direccion);
		}
	}
}


