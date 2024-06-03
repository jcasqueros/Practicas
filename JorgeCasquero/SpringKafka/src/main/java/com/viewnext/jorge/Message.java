package com.viewnext.jorge;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
	
	private String user;
	private String message;
	private boolean primeUse;
	private LocalDateTime timestamp;

}
