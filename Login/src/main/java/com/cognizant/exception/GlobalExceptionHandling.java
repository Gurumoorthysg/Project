package com.cognizant.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandling {
	
	@ExceptionHandler(value=InvalidException.class)
	public ResponseEntity<ErrorInfo> handleIdException(InvalidException inValidException){
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorMessage(inValidException.getMsg());
		errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
		errorInfo.setLocalDateTime(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
	}
	


}
