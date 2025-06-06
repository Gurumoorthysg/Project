package com.cognizant.exception;

public class InvalidException extends RuntimeException {

	
	String msg;
	
	public InvalidException(String msg) {
		super();
		this.msg=msg;
	}
	
	public String getMsg() {
		return msg;
	
	}
}
