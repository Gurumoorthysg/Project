package com.cognizant.exception;

public class InvalidUserException extends RuntimeException {


String msg;
	
	public InvalidUserException(String msg) {
		super();
		this.msg=msg;
	}
	
	public String getMsg() {
		return msg;
	
	}
}

