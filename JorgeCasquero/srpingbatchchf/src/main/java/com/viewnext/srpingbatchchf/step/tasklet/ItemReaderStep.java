package com.viewnext.srpingbatchchf.step.tasklet;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.viewnext.srpingbatchchf.model.TramoCalle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemReaderStep implements Tasklet {

	// nos ayuda a importar archivos desde nuestra carpeta resources
	@Autowired
	private ResourceLoader resourceLoader;

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext/* este chunk nos habre una puerta al contexto de spring bacht */)
			throws Exception {
		log.info("----> Inicio del paso de Lectura  <----");
		Reader reader = new FileReader(
				resourceLoader.getResource("classpath:files/destination/tramos_calle_BarrioDismuni.csv").getFile());

		CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
		CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).withSkipLines(1).build();

		List<TramoCalle> tramoList = new ArrayList<>();
		String[] lineaActual;
		while ((lineaActual = csvReader.readNext()) != null) {
			TramoCalle tramoCalle = new TramoCalle();
			tramoCalle.setCodigoCalle(Long.parseLong(lineaActual[0]));
			tramoCalle.setTipoVia((lineaActual[1]));
			tramoCalle.setNombreCalle(lineaActual[2]);
			tramoCalle.setPrimerNumTramo(Integer.parseInt(lineaActual[3]));
			tramoCalle.setUltimoNumTramo(Integer.parseInt(lineaActual[4]));
			tramoCalle.setBarrio(lineaActual[5]);
			tramoCalle.setCodDistrito(Integer.parseInt(lineaActual[6]));
			tramoCalle.setNomDistrito(lineaActual[7]);
			tramoList.add(tramoCalle);
		}
		csvReader.close();
		reader.close();
		log.info("----> fin del paso de LECTURA <----");

		chunkContext.getStepContext()
		.getStepExecution()
		.getJobExecution()
		.getExecutionContext()
		.put("tramoList", tramoList);
		return RepeatStatus.FINISHED;
	}

}
