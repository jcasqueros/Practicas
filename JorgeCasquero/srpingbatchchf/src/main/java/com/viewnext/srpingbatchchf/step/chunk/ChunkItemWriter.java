package com.viewnext.srpingbatchchf.step.chunk;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.viewnext.srpingbatchchf.model.TramoCalle;
import com.viewnext.srpingbatchchf.service.TramoCalleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChunkItemWriter implements ItemWriter<TramoCalle> {

	@Autowired
	private TramoCalleService tramoCalleService;

	@Override
	public void write(List<? extends TramoCalle> list) throws Exception {
		list.forEach(tramo -> tramo.toString());
		tramoCalleService.saveAll((List<TramoCalle>) list);
	}

}
