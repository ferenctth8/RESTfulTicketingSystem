package com.feritoth.ticketingsystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.feritoth.ticketingsystem.core.ClientTicket;
import com.feritoth.ticketingsystem.service.TicketingService;
import com.feritoth.ticketingsystem.utils.TicketRepositoryProcessor;

@Service("TicketingService")
public class TicketingServiceImpl implements TicketingService {
	
	@Value("${ticketRepositoryLocation}")
	private String ticketRepositoryFileName;
	
	@Autowired
	private TicketRepositoryProcessor ticketRepoProcessor;
	
	@Override
	public List<ClientTicket> getAllTicketsFromRepository() {
		List<ClientTicket> allRegisteredTickets = ticketRepoProcessor.getActualTicketList(ticketRepositoryFileName);
		return allRegisteredTickets;
	}

	@Override
	public ClientTicket generateNewTicket() {		
		//get the actual ticket list to see its current content
		List<ClientTicket> allRegisteredTickets = ticketRepoProcessor.getActualTicketList(ticketRepositoryFileName);
		if (allRegisteredTickets != null && !allRegisteredTickets.isEmpty()){
			//fetch the last waiting number and serial number from the actual list
			int registrySize = allRegisteredTickets.size();
			int lastWaitingNb = allRegisteredTickets.get(registrySize - 1).getWaitingNumber() + 1;
			int lastSerialNb = allRegisteredTickets.get(registrySize - 1).getSerialNumber() + 1;
			//create and register the new ticket
			ClientTicket newTicket = new ClientTicket(lastSerialNb, LocalDateTime.now(), lastWaitingNb);
			allRegisteredTickets.add(newTicket);
			//save the new ticket registry content in the support file
			ticketRepoProcessor.updateTicketRegistry(ticketRepositoryFileName, allRegisteredTickets);
			return newTicket;
		}
		//return null in case the repository is not accessible or the ticket list is empty/null 
		return null;
	}

	@Override
	public ClientTicket returnActiveTicket() {
		List<ClientTicket> allRegisteredTickets = ticketRepoProcessor.getActualTicketList(ticketRepositoryFileName);
		if (allRegisteredTickets == null || allRegisteredTickets.isEmpty()){
			return null;
		}
		return allRegisteredTickets.get(0);		
	}

	@Override
	public ClientTicket eraseLastActiveTicket() {
		//get the actual ticket list to see its current content
		List<ClientTicket> allRegisteredTickets = ticketRepoProcessor.getActualTicketList(ticketRepositoryFileName);
		//safety check: see if the list is empty or null - in that case, return a null element
		if (allRegisteredTickets == null || allRegisteredTickets.isEmpty()){
			return null;
		}
		//in case of no problems encountered, proceed with the processing as follows
		//remove the first element from the ticket queue
		ClientTicket lastActiveTicket =	allRegisteredTickets.remove(0);
		//update the remaining elements - pay special attention to the new first element of the list		
		for (int i = 0; i < allRegisteredTickets.size(); i++){
			allRegisteredTickets.get(i).setWaitingNumber(i + lastActiveTicket.getWaitingNumber());
		}				
		//write the changes into the repository
		ticketRepoProcessor.updateTicketRegistry(ticketRepositoryFileName, allRegisteredTickets);
		//return the removed ticket
		return lastActiveTicket;
	}

}