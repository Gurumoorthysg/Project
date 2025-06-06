package com.cognizant.exception;

public class InvalidDateException extends RuntimeException {

String msg;
	
	
	
	public InvalidDateException(String msg) {
		super();
		this.msg=msg;
	}






	public String getMsg() {
		return msg;
	
	}
}
