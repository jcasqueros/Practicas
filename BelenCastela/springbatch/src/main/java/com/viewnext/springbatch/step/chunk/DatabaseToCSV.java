package com.viewnext.springbatch.step.chunk;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;

import com.viewnext.springbatch.model.Direccion;

public class DatabaseToCSV {
	
	/*public FlatFileItemWriter<Direccion> csvItemWriter() {
	    FlatFileItemWriter<Direccion> writer = new FlatFileItemWriter<>();
	    writer.setResource(new FileSystemResource("src/main/resources/WrittenDownH2.csv"));
	    writer.setLineAggregator(new DelimitedLineAggregator<Direccion>() {
	        {
	            setDelimiter(",");
	            setFieldExtractor(new BeanWrapperFieldExtractor<Direccion>() {
	                {
	                    setNames(new String[] {"codigoCalle", "tipoVia", "nombreCalle", "primerNumTramo", "ultimoNumTramo", "barrio", "codDistrito", "nomDistrito"});
	                }
	            });
	        }
	    });
	    return writer;
	}*/

}