package com.viewnext.srpingbatchchf.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.viewnext.srpingbatchchf.model.TramoCalle;
import com.viewnext.srpingbatchchf.persistence.TramoCalleRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RestController
@RequestMapping("/v1")
public class BachController {

	// joblauncher
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;

	@Autowired
	private TramoCalleRepository tramoCalleRepository;

	@PostMapping("/uploadFile")
	public ResponseEntity<?> recibirArchivo(@RequestParam(name = "file") MultipartFile multipartFile) {

		String fileName = multipartFile.getOriginalFilename();
		try {
			// para que no haya problema con ningun sistema operativo
			Path path = Paths.get("src" + File.separator + "main" + File.separator + "resource" + File.separator
					+ "files" + File.separator + fileName);

			Files.createDirectories(path.getParent());
			Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			log.info("---> Inicio del proceso batch <---");
			JobParameters jobParameters = new JobParametersBuilder().addDate("fecha", new Date())
					.addString("fileName", fileName).toJobParameters();

			jobLauncher.run(job, jobParameters);

			Map<String, String> response = new HashMap<>();
			response.put("archivo", fileName);
			response.put("estado", "recibido");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("Error al inciar el proceso bacth,Error{}", e.getMessage());
			throw new RuntimeException();
		}

	}

	@GetMapping("/getAll")
	public ResponseEntity<List<TramoCalle>> getMethodName() {
		List<TramoCalle> tramosCalle = tramoCalleRepository.findAll();
		return ResponseEntity.ok(tramosCalle);
	}

}
