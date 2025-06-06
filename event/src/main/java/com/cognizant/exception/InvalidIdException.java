package com.cognizant.exception;

public class InvalidIdException extends RuntimeException {

	
	String msg;
	
	public InvalidIdException(String msg) {
		super();
		this.msg=msg;
	}
	
	public String getMsg() {
		return msg;
	
	}
}
