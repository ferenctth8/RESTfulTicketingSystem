package com.feritoth.ticketingsystem;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.feritoth.ticketingsystem.configuration.core.AppConfig;
import com.feritoth.ticketingsystem.core.ClientTicket;
import com.feritoth.ticketingsystem.service.TicketingService;

public class TicketingApp {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TicketingApp.class);
	
    public static void main(String[] args) {
    	//create the context where to locate the service reference
    	AbstractApplicationContext testContext = new AnnotationConfigApplicationContext(AppConfig.class);
    	//get the service in question
    	TicketingService ticketingService = (TicketingService) testContext.getBean("TicketingService");
    	//test the first method - get all tickets from the repository and print them out to the console for verification
    	List<ClientTicket> allRegisteredTickets = ticketingService.getAllTicketsFromRepository();
    	allRegisteredTickets.forEach(ticket -> LOGGER.info(ticket.toString()));
    	//test the second method - register a new ticket
    	ticketingService.generateNewTicket();
    	//test the third method - get the active ticket from the queue and print it to the console for verification
    	ClientTicket activeTicket = ticketingService.returnActiveTicket();
    	LOGGER.info(activeTicket.toString());
    	//test the fourth method - update the ticket repository and remove the first active ticket from it
    	ticketingService.eraseLastActiveTicket();
    	//close the context after finishing the test
    	testContext.close();    	
    }
    
}