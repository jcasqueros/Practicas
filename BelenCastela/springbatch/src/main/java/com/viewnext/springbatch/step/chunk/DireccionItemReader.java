package com.viewnext.springbatch.step.chunk;

import java.nio.charset.StandardCharsets;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.viewnext.springbatch.model.Direccion;

public class DireccionItemReader extends FlatFileItemReader<Direccion>{

	public DireccionItemReader() {
		setName("readDirecciones");
		setResource(new ClassPathResource("tramos_calle_BarrioDismuni.csv"));
		setLinesToSkip(1);
		setEncoding(StandardCharsets.UTF_8.name());
		setLineMapper(getLineMapper());
	}
	
	public LineMapper<Direccion> getLineMapper() {
		DefaultLineMapper<Direccion> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		
		String[] columns= new String []{"CODIGO_CALLE","TIPO_VIA","NOMBRE_CALLE","PRIMER_NUM_TRAMO","ULTIMO_NUM_TRAMO","BARRIO","COD_DISTRITO","NOM_DISTRITO"};
		int[] indexFields = new int[] {0,1,2,3,4,5,6,7};
		
		lineTokenizer.setNames(columns);
		lineTokenizer.setIncludedFields(indexFields);
		lineTokenizer.setDelimiter(",");
		
		BeanWrapperFieldSetMapper<Direccion> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Direccion.class);
		
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		
		return lineMapper;
		
	}
}
