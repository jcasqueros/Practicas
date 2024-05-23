package com.viewnext.srpingbatchchf.step.tasklet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.viewnext.srpingbatchchf.model.TramoCalle;
import com.viewnext.srpingbatchchf.service.TramoCalleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemWriterStep implements Tasklet, StepExecutionListener {

	@Autowired
	private TramoCalleService tramoCalleService;

	private List<TramoCalle> tramoCalles = new ArrayList<>();

	@Override
	public void beforeStep(StepExecution stepExecution) {
		tramoCalles = (List<TramoCalle>) stepExecution.getJobExecution().getExecutionContext().get("tramoCallesMod");
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.info("---> Inicio del paso de escritura <---");

//		System.out.println(tramoCalles.toString());
		tramoCalles.forEach(tramo -> {
			if (tramo != null) {
//				log.info(tramo.toString());
			}
		});
		tramoCalleService.saveAll(tramoCalles);
		log.info("---> Fin del paso de escritura <---");
		return RepeatStatus.FINISHED;
	}

	

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		return null;
	}

}
