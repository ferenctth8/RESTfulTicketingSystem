package com.feritoth.ticketingsystem.exception.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.feritoth.ticketingsystem.exception.FileProcessingException;
import com.feritoth.ticketingsystem.exception.info.ExceptionInfo;

@Provider
public class FileProcessingExceptionHandler implements ExceptionMapper<FileProcessingException> {
	
	@Override
	public Response toResponse(FileProcessingException exception) {
		/* Create the exception info object to be returned */
		ExceptionInfo newExceptionInfo = new ExceptionInfo(exception.getUrl(), exception, Response.Status.NOT_ACCEPTABLE.getStatusCode(), Response.Status.NOT_ACCEPTABLE);
		/* Wrap it into the corresponding Response layer */
		return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode()).entity(newExceptionInfo).build();
	}

}