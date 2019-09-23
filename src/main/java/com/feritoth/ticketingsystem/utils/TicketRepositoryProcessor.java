package com.feritoth.ticketingsystem.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.feritoth.ticketingsystem.core.ClientTicket;
import com.feritoth.ticketingsystem.exception.FileProcessingException;

/**
 * The processing class for the Ticket repository.
 * 
 * @author Ferenc Toth
 */
@Repository("TicketRepository")
public class TicketRepositoryProcessor {
	
	/* Marker for end-of-file position constant */
	public static final String FILE_ENDING = "--End of queue";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TicketRepositoryProcessor.class);
	
	/**
	 * A method for reading the whole content of the ticket registry.
	 * 
	 * @param fileName the name of the file where the list of tickets is held
	 * 
	 * @return the list of currently waiting tickets
	 */
	public List<ClientTicket> getActualTicketList(String fileName) {
		//initialize holding list and decorator for reading
		List<ClientTicket> allRegisteredTickets = new LinkedList<>();		
		//populate the given list
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufReader = new BufferedReader(fileReader);
			//Read the file line by line and process each of the lines which are read as they come up
			String currentTicketLine = bufReader.readLine();
			String[] ticketData = {};
			while (!StringUtils.equalsIgnoreCase(currentTicketLine, FILE_ENDING)) {
				//split the read line
				ticketData = currentTicketLine.split(",");
				//process each of the tokens in question as follows
				int serialNb = convertTokenToNumericalValue(ticketData[0]);
				LocalDateTime arrivalDateTime = convertTokenToLocalDateTime(ticketData[1]);
				int waitingNb = convertTokenToNumericalValue(ticketData[2]);
				//create the resulting ticket and add it to the collection
				allRegisteredTickets.add(new ClientTicket(serialNb, arrivalDateTime, waitingNb));
				//proceed to a new line
				currentTicketLine = bufReader.readLine();
			}			
			//close the reader in question and return the populated list
			bufReader.close();
			return allRegisteredTickets;
		} catch (IOException e) {
			LOGGER.error("The following exception has been detected in the system:" + e.getClass().getName() + " " + e.getMessage() + ", caused by:" + e.getCause());
			return null;
		} catch (NumberFormatException e){
			LOGGER.error("The following exception has been detected in the system:" + e.getClass().getName() + " " + e.getMessage() + ", caused by:" + e.getCause());
			throw e;
		}
	}
	
	/**
	 * A method responsible for refreshing the content of the ticket registry.
	 * 
	 * @param filename the name of the registry file
	 * @param clientTickets the new ticket list
	 */
	public void updateTicketRegistry(String filename, List<ClientTicket> clientTickets) {
		try {
			FileWriter fw = new FileWriter(filename);
			BufferedWriter bw = new BufferedWriter(fw);
			//iterate through the list and perform the conversion of the ticket under a suitable output form
			for (ClientTicket clientTicket : clientTickets) {
				String currentTicket = clientTicket.getSerialNumber() + "," + convertArrivalDateTime(clientTicket.getArrivalDateTime()) + "," + clientTicket.getWaitingNumber();
				bw.write(currentTicket);
				bw.newLine();
			}
			//close the file and deal with any arising exception
			bw.write(FILE_ENDING);
			bw.close();
		} catch (IOException e) {
			LOGGER.error("The following exception has been detected in the system:" + e.getClass().getName() + " " + e.getMessage() + ", caused by:" + e.getCause());
			throw new FileProcessingException(e.getMessage(), e);
		}		
	}

	//private LocalDateTime-to-String converter method
	private static String convertArrivalDateTime(LocalDateTime arrivalDateTime) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return arrivalDateTime.format(dtf);
	}

	//private String-to-LocalDateTime converter method
	private static LocalDateTime convertTokenToLocalDateTime(String dateTimeToken) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return LocalDateTime.parse(dateTimeToken, dtf);
	}

	//private numerical converter method
	private static int convertTokenToNumericalValue(String numericalToken) throws NumberFormatException {
		return Integer.valueOf(numericalToken);
	}

}