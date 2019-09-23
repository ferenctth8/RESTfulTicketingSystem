package com.feritoth.ticketingsystem.exception;

public class FileProcessingException extends RuntimeException {

	private static final long serialVersionUID = 2764778200771788061L;
	
	private Throwable cause;
	private String message;
	private String url;
	
	public FileProcessingException() {
		super();		
	}
	
	public FileProcessingException(String message, Throwable cause) {
		super(message, cause);
		this.cause = cause;
		this.message = message;
	}
	
	public FileProcessingException(String message, String finalURL) {
		super(message);
		this.message = message;
		this.url = finalURL;
	}
	
	public FileProcessingException(Throwable cause) {
		super(cause);
		this.cause = cause;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}	

}