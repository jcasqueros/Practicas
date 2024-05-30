package com.viewnext.jorge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

public class ConsumidorTest {

	private Consumidor consumidor;
	private static final Logger logger = LoggerFactory.getLogger(Consumidor.class);

	@BeforeEach
	public void setUp() {
		consumidor = new Consumidor();
	}

	@Test
	public void testListen() {
		// Arrange
		Message message = new Message("user1", "HolaMundo", true, LocalDateTime.now());

		// Act
		consumidor.listen(message);

		// assert
		
	}
}
