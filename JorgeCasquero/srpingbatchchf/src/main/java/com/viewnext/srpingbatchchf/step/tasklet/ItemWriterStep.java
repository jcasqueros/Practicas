package com.viewnext.srpingbatchchf.step.tasklet;

import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.viewnext.srpingbatchchf.model.TramoCalle;
import com.viewnext.srpingbatchchf.service.TramoCalleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemWriterStep implements Tasklet {
	private TramoCalleService tramoCalleService;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.info("---> Inicio del paso de escritura <---");

		List<TramoCalle> tramoCalles = (List<TramoCalle>) chunkContext.getStepContext().getStepExecution()
				.getExecutionContext().get("tramoCallesMod");

		tramoCalles.forEach(tramo -> {
			if (tramo != null) {
				log.info(tramo.toString());
			}
		});
		tramoCalleService.saveAll(tramoCalles);
		log.info("---> Fin del paso de escritura <---");
		return RepeatStatus.FINISHED;
	}

}
