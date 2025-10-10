package com.example.hotelManagement.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.hotelManagement.model.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException ex) {
		return new ResponseEntity<>(new ApiResponse("GETFAILS", ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceAlreadyExistException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceAlreadyExistException ex) {
		return new ResponseEntity<>(new ApiResponse("ADDFAILS", ex.getMessage()), HttpStatus.ALREADY_REPORTED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
		return new ResponseEntity<>(new ApiResponse("ERROR", "An unexpected error occurred: " + ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
