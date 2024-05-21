package com.viewnext.srpingbatchchf.step.tasklet;

import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import com.viewnext.srpingbatchchf.model.TramoCalle;
import com.viewnext.srpingbatchchf.persistence.TramoCalleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemProcessorStep implements Tasklet, StepExecutionListener {

	@Autowired
	TramoCalleRepository tramoCalleRepository;
	private List<TramoCalle> tramoCalles;
	private List<TramoCalle> tramoCallesMod;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		tramoCalles = (List<TramoCalle>) stepExecution.getJobExecution().getExecutionContext().get("tramoList");

	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
			throws Exception {
		log.info("----> Inicio del paso de Procesamiento<----");

		// escribir en la bdd las los tramos que pertenezcan aun determinado distrito
		tramoCallesMod = tramoCalles.stream().filter(tramoCalle -> tramoCalle.getCodDistrito() == 2).toList();

		log.info("----> Fin del paso de Procesamiento<----");
		return RepeatStatus.FINISHED;

	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		stepExecution.getJobExecution().getExecutionContext().put("tramoCallesMod", tramoCallesMod);
		return ExitStatus.COMPLETED;
	}

//	
}