package com.zabid.threadhouse.exception;

import java.util.Date;

public class ErrorDetails {

	private String details;
	private String message;
	private Date timeStamp;
	
	
	public ErrorDetails(String details, String message, Date timeStamp) {
		super();
		this.details = details;
		this.message = message;
		this.timeStamp = timeStamp;
	}


	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Date getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
