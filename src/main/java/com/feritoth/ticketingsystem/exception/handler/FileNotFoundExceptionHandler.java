package com.feritoth.ticketingsystem.exception.handler;

import java.io.FileNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.feritoth.ticketingsystem.exception.info.ExceptionInfo;

@Provider
public class FileNotFoundExceptionHandler implements ExceptionMapper<FileNotFoundException> {

	@Override
	public Response toResponse(FileNotFoundException exception) {
		/* Split the generated exception message and retain the tokens */
		String[] tokens = exception.getMessage().split("\\+");		
		/* Create the exception info object to be returned */
		ExceptionInfo newExceptionInfo = new ExceptionInfo(tokens[1], tokens[0], Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND);
		/* Wrap it into the corresponding Response layer */
		return Response.status(Response.Status.NOT_FOUND.getStatusCode()).entity(newExceptionInfo).build();
	}

}