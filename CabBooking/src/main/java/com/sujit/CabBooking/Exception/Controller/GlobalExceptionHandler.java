package com.sujit.CabBooking.Exception.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sujit.CabBooking.Exception.DriverNotFoundException;
import com.sujit.CabBooking.Exception.NoAvailableDriverException;
import com.sujit.CabBooking.Exception.RideNotFoundException;
import com.sujit.CabBooking.Exception.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	    
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
	    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DriverNotFoundException.class)
	public ResponseEntity<String> handleDriverNotFoundException(DriverNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(RideNotFoundException.class)
	public ResponseEntity<String> handleRideNotFoundException(RideNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoAvailableDriverException.class)
	public ResponseEntity<String> NoAvailableDriverException(NoAvailableDriverException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
