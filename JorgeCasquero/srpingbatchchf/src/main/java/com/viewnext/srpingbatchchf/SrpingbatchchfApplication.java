package com.viewnext.srpingbatchchf;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SrpingbatchchfApplication {

	@Autowired
	private Job job;
	@Autowired
	private JobLauncher jobLauncher;

	public static void main(String[] args) {
		SpringApplication.run(SrpingbatchchfApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return args ->{
			JobParameters jobParameters=new JobParametersBuilder()
					.addString("name","chunk")
					.addLong("id",System.currentTimeMillis())
					.addDate("date",new Date())
					.toJobParameters();
			jobLauncher.run(job, jobParameters);
		};
	}
}
