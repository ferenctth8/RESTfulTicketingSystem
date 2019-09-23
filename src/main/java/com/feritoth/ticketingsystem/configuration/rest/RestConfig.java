package com.feritoth.ticketingsystem.configuration.rest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.feritoth.ticketingsystem.exception.handler.FileNotFoundExceptionHandler;
import com.feritoth.ticketingsystem.exception.handler.FileProcessingExceptionHandler;
import com.feritoth.ticketingsystem.exception.handler.NotFoundExceptionHandler;
import com.feritoth.ticketingsystem.exception.handler.NumberFormatExceptionHandler;
import com.feritoth.ticketingsystem.rest.TicketRestController;

@ApplicationPath("/resources")
public class RestConfig extends Application {
	
	public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(Arrays.asList(TicketRestController.class, FileProcessingExceptionHandler.class, 
        		                                   NumberFormatExceptionHandler.class, FileNotFoundExceptionHandler.class,
        		                                   NotFoundExceptionHandler.class));
    }

}