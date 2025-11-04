package com.monitoredrx.patientservice.exception;

import lombok.Getter;

public class DataAlreadyExistsException extends RuntimeException {

	@Getter
	private String loggerMessage;
	
	public DataAlreadyExistsException(String message) {
        super(message);
    }
	
	public DataAlreadyExistsException(String message, String loggerMessage) {
        super(message);
        this.loggerMessage = loggerMessage;
    }
	
}
