package com.viewnext.springbatch.config;

import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class ApplicationConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	/*@Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:batch")
                .username("sa")
                .password("")
                .build();
    }*/
	
	/*@Bean
	public DataSource dataSource() {
		DriverManagerDataSource driverManager = new DriverManagerDataSource();
		driverManager.setDriverClassName("com.mysql.cj.jdbc.Driver");
		driverManager.setUrl("jdbc:mysql://localhost:3306/batch_chunk");
		driverManager.setUsername("root");
		driverManager.setPassword("");
		
		return driverManager;
	}*/
}
