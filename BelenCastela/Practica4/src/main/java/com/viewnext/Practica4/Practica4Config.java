package com.viewnext.Practica4;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Practica4Config {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
