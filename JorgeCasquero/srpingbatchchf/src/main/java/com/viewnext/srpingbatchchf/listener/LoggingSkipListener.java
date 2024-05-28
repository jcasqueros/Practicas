package com.viewnext.srpingbatchchf.listener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

import com.viewnext.srpingbatchchf.model.TramoCalle;

@Component
public class LoggingSkipListener implements SkipListener<TramoCalle, TramoCalle> {

	private static final String LOG_FILE = "error.log";

	@Override
	public void onSkipInRead(Throwable t) {
		if (t instanceof FlatFileParseException) {
			FlatFileParseException ffpe = (FlatFileParseException) t;
			System.err.println("Error en la línea: " + ffpe.getInput());
		}
	}

	@Override
	public void onSkipInWrite(TramoCalle item, Throwable t) {
		writer("Error al escribir el item: " + item.toString(), t);
	}

	@Override
	public void onSkipInProcess(TramoCalle item, Throwable t) {
		writer("Error al procesar el item: " + item.toString(), t);
	}

	private void writer(String message, Throwable t) {
		try (FileWriter fw = new FileWriter(LOG_FILE, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
