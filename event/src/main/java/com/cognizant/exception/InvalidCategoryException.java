package com.cognizant.exception;

public class InvalidCategoryException extends RuntimeException {

String msg;
	
	 public InvalidCategoryException(String msg) {
		// TODO Auto-generated constructor stub
		 super();
		 this.msg=msg;
		
	}
	
	public String getMsg() {
		return msg;
	
	}
}
