package com.viewnext.srpingbatchchf.step.chunk;

import java.nio.charset.StandardCharsets;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

import com.viewnext.srpingbatchchf.model.TramoCalle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChunkItemReader extends FlatFileItemReader<TramoCalle> {

	public ChunkItemReader() {

		setName("readTramo");
		setResource(new ClassPathResource("files/tramos_calle_BarrioDismuni.csv"));
		setLinesToSkip(1);// saltar primera linea
		setEncoding(StandardCharsets.UTF_8.name());
//		setSkippedLinesCallback(null)
		try {
			setLineMapper(getLineMapper());
		} catch (FlatFileParseException e) {
			System.out.println(e.getMessage() + "no se ha podido procesar la linea");
		}

	}

	public LineMapper<TramoCalle> getLineMapper() {
		DefaultLineMapper<TramoCalle> lineMapper = new DefaultLineMapper<>();// obtener las lineas aqui llega la line //
// completa
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(); // dividir las lineas similar al split

		String[] columns = new String[] { "CODIGO_CALLE", "TIPO_VIA", "NOMBRE_CALLE", "PRIMER_NUM_TRAMO",
				"ULTIMO_NUM_TRAMO", "BARRIO", "COD_DISTRITO", "NOM_DISTRITO" };
		int[] indexFields = new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };

		lineTokenizer.setNames(columns);
		lineTokenizer.setIncludedFields(indexFields);
		// lineTokenizer.setDelimiter(new DelimitedLineTokenizer()); por defecto spring
// batch usa el delimitador por defecto ","
		log.info("Se comienza a leer");
		BeanWrapperFieldSetMapper<TramoCalle> fieldSetMapper = new BeanWrapperFieldSetMapper<>();

		fieldSetMapper.setTargetType(TramoCalle.class);

		lineMapper.setLineTokenizer(lineTokenizer);

		lineMapper.setFieldSetMapper(fieldSetMapper);
		System.out.println(lineMapper.toString());

		return lineMapper;

	}

}
