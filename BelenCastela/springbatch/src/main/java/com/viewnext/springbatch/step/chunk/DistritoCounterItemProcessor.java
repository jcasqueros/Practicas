package com.viewnext.springbatch.step.chunk;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.viewnext.springbatch.model.Direccion;
import com.viewnext.springbatch.model.Distrito;
import com.viewnext.springbatch.service.DistritoService;

public class DistritoCounterItemProcessor implements ItemProcessor<Direccion, Direccion> {

	@Autowired
	private DistritoService distritoService;

	private Map<String, Integer> distritoCount = new HashMap<>();

	@Override
	public Direccion process(Direccion item) throws Exception {
		String nomDistrito = item.getNomDistrito();
		distritoCount.put(nomDistrito, distritoCount.getOrDefault(nomDistrito, 0) + 1);
		return item;
	}

	@AfterStep
	public void afterStep() {
		for (Map.Entry<String, Integer> entry : distritoCount.entrySet()) {
			Distrito distrito = new Distrito();
			distrito.setNomDistrito(entry.getKey());
			distrito.setNumCasas(entry.getValue());
			distritoService.save(distrito);
		}
	}
}

