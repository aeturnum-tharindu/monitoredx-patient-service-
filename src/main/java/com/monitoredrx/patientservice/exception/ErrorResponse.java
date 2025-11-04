package com.monitoredrx.patientservice.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorResponse", description = "Standard error response for API failures")
public record ErrorResponse(
		
		@Schema(description = "HTTP status code", example = "400")
		int status, 
		
		@Schema(description = "HTTP error name", example = "Bad Request")
		String error, 
		
		@Schema(description = "Error message", example = "Patient email/phone already exists")
		String message) {
}
