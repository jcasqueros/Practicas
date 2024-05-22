package com.viewnext.srpingbatchchf.step.tasklet;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.viewnext.srpingbatchchf.model.TramoCalle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ItemReaderStep implements Tasklet, StepExecutionListener {

	// nos ayuda a importar archivos desde nuestra carpeta resources
	@Autowired
	private ResourceLoader resourceLoader;

	List<TramoCalle> tramoList = new ArrayList<>();

	@Override
	public void beforeStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub

	}

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext/* este chunk nos habre una puerta al contexto de spring bacht */)
			throws Exception {
		log.info("----> Inicio del paso de Lectura  <----");
		Reader reader = new FileReader(
				resourceLoader.getResource("classpath:files/tramos_calle_BarrioDismuni.csv").getFile());

		CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
		CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).withSkipLines(1).build();

		String[] lineaActual;
		while ((lineaActual = csvReader.readNext()) != null) {
			try {
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

			} catch (Exception e) {
				log.error("eror al formatear tramo de calle:{}", lineaActual);
				writeLog(lineaActual);
			}

		}

		csvReader.close();
		reader.close();
		log.info("----> fin del paso de LECTURA <----");

		return RepeatStatus.FINISHED;
	}

	private void writeLog(String[] linea) {
		try (FileWriter fw = new FileWriter("errors.log", true)) {
			fw.write(Arrays.toString(linea) + "\n");

		} catch (IOException e) {
			log.error("error al escribir en el ficheo de log:{}", e.getMessage());
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {

		stepExecution.getJobExecution().getExecutionContext().put("tramoList", tramoList);
		return ExitStatus.COMPLETED;
	}

}
