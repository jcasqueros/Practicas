package com.viewnext.srpingbatchchf.step.tasklet;

import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.viewnext.srpingbatchchf.model.TramoCalle;
import com.viewnext.srpingbatchchf.persistence.TramoCalleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemProcessorStep implements Tasklet {

	@Autowired
	TramoCalleRepository tramoCalleRepository;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.info("----> Inicio del paso de Procesamiento<----");

		List<TramoCalle> tramoCalles = (List<TramoCalle>) chunkContext.getStepContext().getStepExecution()
				.getExecutionContext().get("tramoList");

		List<TramoCalle> tramoCallesMod = tramoCalles.stream().filter(tramoCalle -> tramoCalle.getCodDistrito() == 2)
				.toList();

		// escribir en la bdd las los tramos que pertenezcan aun determinado distrito

		chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("tramoCallesMod",
				tramoCallesMod);
		log.info("----> Fin del paso de Procesamiento<----");
		return RepeatStatus.FINISHED;

	}
}