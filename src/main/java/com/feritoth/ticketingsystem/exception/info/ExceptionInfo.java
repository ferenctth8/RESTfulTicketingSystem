package com.feritoth.ticketingsystem.exception.info;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The class that supplies the error templates for the user-friendly error detection. 
 * 
 * @author Frantisek Slovak
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionInfo implements Serializable {
	
	private static final long serialVersionUID = 2682964519429968972L;
	
	// List of attributes
	private String url;
    private String exceptionMessage;
    private int errorCode;
    private Response.Status httpOperationStatus;

    /**
     * Default class constructor - used for allowing the return of the exception content inside a message written in XML.
     */
    public ExceptionInfo() {
		super();
	}

	/**
     * First parameterized class constructor - used for user-friendly exception creation on the server side.
     * 
     * @param url the location where the exception has been detected
     * @param detectedException the detected exception which was thrown by the invoked REST method
     * @param detectedErrorCode the code of the error state
     * @param operationStatus the operation status
     */
    public ExceptionInfo(String url, Exception detectedException, int detectedErrorCode, Response.Status operationStatus) {
        super();
        this.url = url;
        this.exceptionMessage = detectedException.getMessage();
        this.errorCode = detectedErrorCode;
        this.httpOperationStatus = operationStatus;
    }   

    /**
     * Second parameterized class constructor - used for exception handling on the client side.
     * 
     * @param url the location where the exception has been detected
     * @param exceptionMessage the message of the detected exception which was thrown by the invoked REST method
     * @param detectedErrorCode the code of the error state
     * @param operationStatus the operation status
     */
    public ExceptionInfo(String url, String exceptionMessage, int detectedErrorCode, Response.Status operationStatus) {
		super();
		this.url = url;
		this.exceptionMessage = exceptionMessage;
		this.errorCode = detectedErrorCode;
		this.httpOperationStatus = operationStatus;
	}
    
    /**
     * @return the URL of the invoked REST method
     */
	public String getUrl() {
		return url;
	}
	
	/**
	 * @param url - the URL of the invoked REST method
	 */
	public void setUrl(String url) {
		this.url = url;
	}
    
	/**
	 * @return the error code carried by the identified exception
	 */
	public int getErrorCode() {
		return errorCode;
	}
	
	/**
	 * @param errorCode - the error code carried by the identified exception
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	/**
	 * @return the final operation status
	 */
	public Response.Status getHttpOperationStatus() {
		return httpOperationStatus;
	}
	
	/**
	 * @param httpOperationStatus - the final operation status
	 */
	public void setHttpOperationStatus(Response.Status httpOperationStatus) {
		this.httpOperationStatus = httpOperationStatus;
	}

	/**
	 * @return the message carried by the detected exception
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	
	/**
	 * @param exceptionMessage - the message carried by the detected exception
	 */
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	@Override
	public String toString() {
		return "ExceptionInfo [url=" + url + ", exceptionMessage=" + exceptionMessage + ", errorCode=" + errorCode + ", httpOperationStatus=" + httpOperationStatus + "]";
	}
	
}