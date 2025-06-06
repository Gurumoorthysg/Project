package com.cognizant.exception;
 
public class TicketOverFlowException extends RuntimeException{
 
	String msg;
	public TicketOverFlowException(String msg) {
		super();
		this.msg=msg;
	}
	public String getMsg() {
		return msg;
	}
	
	
	
}