package com.cognizant.exception;
 
public class InvalidCancelRequestException extends RuntimeException{
 
	String msg;
	public InvalidCancelRequestException(String msg) {
		super();
		this.msg=msg;
	}
	public String getMsg() {
		return msg;
	}
	
	
}