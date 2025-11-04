package com.monitoredrx.patientservice.exception;

import lombok.Getter;

public class EntityNotFoundException extends RuntimeException {

	@Getter
	private String loggerMessage;
	
	public EntityNotFoundException(String message) {
        super(message);
    }
	
	public EntityNotFoundException(String message, String loggerMessage) {
        super(message);
        this.loggerMessage = loggerMessage;
    }
}
