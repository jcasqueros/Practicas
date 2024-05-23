package com.viewnext.srpingbatchchf.step.tasklet;

import java.util.HashMap;
import java.util.Map;

//	Crear una tabla donde guardaremos los distritos y el “número de viviendas” (las veces que se repite dicho distrito). 
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class ItemProcessorFilterStep implements Tasklet, StepExecutionListener {

	private Map<String, Integer> contador = new HashMap<>();

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		// Lógica para procesar los datos y recolectar los distritos
		// Simulación de procesamiento de datos
		String[] districts = { "District1", "District2", "District1", "District3" };
		for (String district : districts) {
			collectDistrict(district);
		}
		return RepeatStatus.FINISHED;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		// Inicializa o limpia el mapa antes de ejecutar el Step
		contador.clear();
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// Aquí puedes procesar y mostrar los resultados después de la ejecución del
		// Step
		// Por ejemplo, imprimir los resultados:
		contador.forEach((distrito, cont) -> {
			System.out.println("District: " + distrito + ", Count: " + cont);
		});
		return ExitStatus.COMPLETED;
	}

	private void collectDistrict(String district) {
		contador.put(district, contador.getOrDefault(district, 0) + 1);
	}
}
