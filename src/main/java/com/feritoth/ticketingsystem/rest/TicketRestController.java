package com.feritoth.ticketingsystem.rest;

import java.io.FileNotFoundException;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.feritoth.ticketingsystem.core.ClientTicket;
import com.feritoth.ticketingsystem.exception.FileProcessingException;
import com.feritoth.ticketingsystem.service.TicketingService;

@Path("/tickets")
public class TicketRestController {
	
	private static final String BASE_URL = "http://localhost:8084/RESTfulTicketingSystem/resources/tickets";
	
	@Autowired
	private TicketingService ticketingService;
		
	@GET
	@Path("/allTickets")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllRegisteredTickets() throws FileNotFoundException {
		String finalURL = BASE_URL + "/allTickets";
		List<ClientTicket> allTickets = ticketingService.getAllTicketsFromRepository();
		if (allTickets == null || allTickets.isEmpty()) {
			//throw new FileProcessingException("The ticket repository is currently inaccessible or empty!", finalURL);
			throw new FileNotFoundException("The ticket repository is currently inaccessible or empty!" + "+" + finalURL);
		}
		return Response.status(Response.Status.OK).entity(allTickets).build();
	}
	
	@GET
	@Path("/actualActiveTicket")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActualActiveTicket() {
		String finalURL = BASE_URL + "/actualActiveTicket";
		ClientTicket actualActiveTicket = ticketingService.returnActiveTicket();
		if (actualActiveTicket == null) {
			//throw new FileProcessingException("The ticket repository is currently inaccessible or empty!No active ticket has currently been found as available!", finalURL);
			throw new NotFoundException("The ticket repository is currently inaccessible or empty!No active ticket has currently been found as available!+" + finalURL);
		}
		return Response.status(Response.Status.OK).entity(actualActiveTicket).build();
	}
	
	@POST
	@Path("/registerNewTicket")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerNewTicket() {
		String finalURL = BASE_URL + "/registerNewTicket";
		ClientTicket generatedTicket = ticketingService.generateNewTicket();
		if (generatedTicket == null) {
			throw new FileProcessingException("The ticket repository is currently inaccessible or empty!No new ticket registration is therefore possible at the moment!", finalURL);
		}		
		return Response.status(Response.Status.CREATED).entity(generatedTicket).build();
	}
	
	@DELETE
	@Path("/removeLastActiveTicket")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeLastActiveTicket() {
		String finalURL = BASE_URL + "/removeLastActiveTicket";
	    ClientTicket lastActiveTicket = ticketingService.eraseLastActiveTicket();
	    if (lastActiveTicket == null) {
	    	throw new FileProcessingException("The ticket repository is currently inaccessible or empty!No ticket removal operation is therefore possible at the moment!", finalURL);
	    }
	    String removalOutcome = "The following ticket has been removed from the queue:" + lastActiveTicket.toString();	    
	    return Response.status(Response.Status.RESET_CONTENT).header("Removal-Success", removalOutcome).build();
	}

}