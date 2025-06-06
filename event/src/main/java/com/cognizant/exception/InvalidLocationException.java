package com.cognizant.exception;

public class InvalidLocationException extends RuntimeException {

String msg;
	
	public InvalidLocationException(String msg) {
		super();
		this.msg=msg;
	}
	
	public String getMsg() {
		return msg;
	
	}
}
