package com.feritoth.ticketingsystem.exception.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.feritoth.ticketingsystem.exception.info.ExceptionInfo;

@Provider
public class NumberFormatExceptionHandler implements ExceptionMapper<NumberFormatException> {

	@Override
	public Response toResponse(NumberFormatException exception) {
		/* Create the exception info object to be returned */
		ExceptionInfo newExceptionInfo = new ExceptionInfo(null, exception, Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST);
		/* Wrap it into the corresponding Response layer */
		return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(newExceptionInfo).build();
	}

}