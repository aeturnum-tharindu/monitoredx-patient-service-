package com.monitoredrx.patientservice.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class GlobalExceptionHandler.
 */
@Slf4j
public abstract class GlobalExceptionHandler {

	  	/**
	  	 * Handle data already exists exception.
	  	 *
	  	 * @param ex the ex
	  	 * @param request the request
	  	 * @return the response entity
	  	 */
	  	@ExceptionHandler(DataAlreadyExistsException.class)
		public ResponseEntity<ErrorResponse> handleDataAlreadyExistsException(DataAlreadyExistsException ex,
				HttpServletRequest request) {
	  		
	  		log.error(ex.getLoggerMessage(), ex);
	  		
	        ErrorResponse error = new ErrorResponse(
	                HttpStatus.BAD_REQUEST.value(),
	                HttpStatus.BAD_REQUEST.getReasonPhrase(),
	                ex.getMessage()
	        );

	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(error);
	    }
	  	
	  	/**
	  	 * Handle entity not found exception.
	  	 *
	  	 * @param ex the ex
	  	 * @param request the request
	  	 * @return the response entity
	  	 */
	  	@ExceptionHandler(EntityNotFoundException.class)
		public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex,
				HttpServletRequest request) {
	  		
	  		log.error(ex.getLoggerMessage(), ex);
	  		
	        ErrorResponse error = new ErrorResponse(
	                HttpStatus.NOT_FOUND.value(),
	                HttpStatus.NOT_FOUND.getReasonPhrase(),
	                ex.getMessage()
	        );

	        return ResponseEntity
	                .status(HttpStatus.NOT_FOUND)
	                .body(error);
	    }
	  	
	  	/**
	  	 * Handle validation exceptions.
	  	 *
	  	 * @param ex the ex
	  	 * @return the response entity
	  	 */
	  	@ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap();
	        ex.getBindingResult().getFieldErrors().forEach(error -> {
	            errors.put(error.getField(), error.getDefaultMessage());
	        });
	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	    }
	
}
