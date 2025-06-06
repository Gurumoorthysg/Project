package com.cognizant.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandling {
	
	@ExceptionHandler(value=InvalidIdException.class)
	public ResponseEntity<ErrorInfo> handleIdException(InvalidIdException inValidIdException){
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorMessage(inValidIdException.getMsg());
		errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
		errorInfo.setLocalDateTime(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value=InvalidLocationException.class)
	public ResponseEntity<ErrorInfo> handleLocationException(InvalidLocationException inValidLocationException){
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorMessage(inValidLocationException.getMsg());
		errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
		errorInfo.setLocalDateTime(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value=InvalidDateException.class)
	public ResponseEntity<ErrorInfo> handleDateException(InvalidLocationException inValidLocationException){
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorMessage(inValidLocationException.getMsg());
		errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
		errorInfo.setLocalDateTime(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value=InvalidCategoryException.class)
	public ResponseEntity<ErrorInfo> handleCategoryException(InvalidCategoryException inValidCategoryException){
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorMessage(inValidCategoryException.getMsg());
		errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
		errorInfo.setLocalDateTime(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value=InvalidUserException.class)
	public ResponseEntity<ErrorInfo> handleIdException(InvalidUserException inValidUserException){
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorMessage(inValidUserException.getMsg());
		errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
		errorInfo.setLocalDateTime(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
	}

}
