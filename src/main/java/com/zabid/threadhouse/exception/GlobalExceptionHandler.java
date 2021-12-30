package com.zabid.threadhouse.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	// invalid user name and password
	@ExceptionHandler(UserAuthenticationException.class)
	public ResponseEntity<ErrorDetails> userAuthenticationException(UserAuthenticationException e, WebRequest wr){
		ErrorDetails errorDetails = getErrorDetails(e, wr);
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> resourceNotFoundException(ResourceNotFoundException e, WebRequest wr){
		ErrorDetails errorDetails = getErrorDetails(e, wr);
		return new ResponseEntity<>(errorDetails, HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(DuplicateRecordException.class)
	public ResponseEntity<ErrorDetails> duplicateRecordException(DuplicateRecordException e, WebRequest wr){
		ErrorDetails errorDetails = getErrorDetails(e, wr);
		return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
	}
	
	private ErrorDetails getErrorDetails(Exception e, WebRequest wr) {
		// get the logger here
		return new ErrorDetails(wr.getDescription(true), e.getMessage(), new Date());
	}
}
