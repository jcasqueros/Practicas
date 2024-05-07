package com.example.demo.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
//@Aspect
//@Slf4j
public class Config {

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();

	}

//	@Before(value = "execution(* *(..))")
//	public void controllerLog(JoinPoint joinPoint) {
//		String clase = joinPoint.getTarget().getClass().getSimpleName();
//		String signature = joinPoint.getTarget().getClass().getName();
//		Object[] args = joinPoint.getArgs();
//		String argumentos = "";
//		for (Object arg : args) {
//			if (arg != null) {
//				argumentos += "\nTipo: " + arg.getClass().getName() + "|Valor: " + arg.toString() + ".\n";
//			} else {
//				argumentos += "\nargumento nulo.\n";
//			}
//
//		}
//		log.info("Capa de presentacion:[{}] en la clase [{}]", signature, clase, argumentos);
//	}
//
//	@Before(value = "execution(* *(..))")
//	public void serviceLog(JoinPoint joinPoint) {
//		String clase = joinPoint.getTarget().getClass().getSimpleName();
//		String signature = joinPoint.getTarget().getClass().getName();
//		log.info("Capa de negocio [{}] en la clase[{}]", signature, clase);
//
//	}

}
