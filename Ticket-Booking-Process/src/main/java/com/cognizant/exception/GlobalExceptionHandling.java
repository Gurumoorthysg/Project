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
	@ExceptionHandler(value=InvalidCancelRequestException.class)
	public ResponseEntity<ErrorInfo> handleCancelRequestException(InvalidCancelRequestException inValidCancelRequestException){
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorMessage(inValidCancelRequestException.getMsg());
		errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
		errorInfo.setLocalDateTime(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(value=TicketOverFlowException.class)
	public ResponseEntity<ErrorInfo> ticketOverflowException(TicketOverFlowException ticketOverFlowException){
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorMessage(ticketOverFlowException.getMsg());
		errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST);
		errorInfo.setLocalDateTime(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
	}

 
}