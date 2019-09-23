package com.feritoth.ticketingsystem.service;

import java.util.List;

import com.feritoth.ticketingsystem.core.ClientTicket;

public interface TicketingService {
	
	List<ClientTicket> getAllTicketsFromRepository();
	
	ClientTicket generateNewTicket();
	
	ClientTicket returnActiveTicket();
	
	ClientTicket eraseLastActiveTicket();

}