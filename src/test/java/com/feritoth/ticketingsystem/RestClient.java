package com.feritoth.ticketingsystem;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feritoth.ticketingsystem.core.ClientTicket;

public class RestClient {
	
	private static final String REST_URI = "http://localhost:8084/RESTfulTicketingSystem/resources/tickets";
	private static final Client TEST_CLIENT = ClientBuilder.newClient();
	private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
	
	private static final String[] ALL_TICKET_KEYS = {"serialNumber", "arrivalDateTime", "waitingNumber"};
	
	@SuppressWarnings("unchecked")
	public static void getAllTickets() throws IOException{
		//Build the URL and dispatch the request
		String finalUrl = REST_URI + "/allTickets";
		//Check which kind of response has been received back from the system - whether it is an exception or the desired content
	    Response allResponse = (Response) TEST_CLIENT.target(finalUrl).request(MediaType.APPLICATION_JSON).get(Response.class);
		if (allResponse.getStatus() == Response.Status.OK.getStatusCode()){
			List<LinkedHashMap<String, Object>> allTickets = allResponse.readEntity(List.class);
		    allTickets.forEach(ticketMap -> {
		    	//Format the integer parameters first
		    	Integer serialNumber = (Integer) ticketMap.get(ALL_TICKET_KEYS[0]);
		    	Integer waitingNumber = (Integer) ticketMap.get(ALL_TICKET_KEYS[2]);
		    	//Then format the arrival date and time
		    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		    	LocalDateTime arrivalDateTime = LocalDateTime.parse((String)ticketMap.get(ALL_TICKET_KEYS[1]), dtf);
		    	//Create and print out the ticket for verification
		    	ClientTicket ct = new ClientTicket(serialNumber, arrivalDateTime, waitingNumber);
		    	LOGGER.info(ct.toString());
		    });
		} else {
			String content = allResponse.readEntity(String.class);
        	prettyPrintJSONcontent(new ByteArrayInputStream(content.getBytes()));
		}
	}
	
	public static void getActualActiveTicket() throws IOException {
		String finalUrl = REST_URI + "/actualActiveTicket";
		Response activeTicketResponse = (Response) TEST_CLIENT.target(finalUrl).request(MediaType.APPLICATION_JSON).get(Response.class);
		if (activeTicketResponse.getStatus() == Response.Status.OK.getStatusCode()){
			ClientTicket activeTicket = activeTicketResponse.readEntity(ClientTicket.class);
			LOGGER.info(activeTicket.toString());
		} else {
			String content = activeTicketResponse.readEntity(String.class);
        	prettyPrintJSONcontent(new ByteArrayInputStream(content.getBytes()));
		}		
	}
	
	public static void createNewTicket() throws IOException {
		String finalUrl = REST_URI + "/registerNewTicket";
		Response registrationResponse = TEST_CLIENT.target(finalUrl).request(MediaType.APPLICATION_JSON).post(null, Response.class);
		if (registrationResponse.getStatus() == Response.Status.CREATED.getStatusCode()){
			ClientTicket newTicket = registrationResponse.readEntity(ClientTicket.class);
			LOGGER.info("The newly registered ticket is:" + newTicket);
		} else {
			String content = registrationResponse.readEntity(String.class);
        	prettyPrintJSONcontent(new ByteArrayInputStream(content.getBytes()));
		}
	}
	
	public static void removeLastActiveTicket() throws IOException {
		String finalUrl = REST_URI + "/removeLastActiveTicket";		
		Response removalResponse = (Response) TEST_CLIENT.target(finalUrl).request(MediaType.APPLICATION_JSON).delete(Response.class);
		if (removalResponse.getStatus() == Response.Status.RESET_CONTENT.getStatusCode()){
			String ticketRemovalResult = removalResponse.getHeaderString("Removal-Success");
			LOGGER.info(ticketRemovalResult);
		} else {
			String content = removalResponse.readEntity(String.class);
        	prettyPrintJSONcontent(new ByteArrayInputStream(content.getBytes()));
		}
	}
	
	private static void prettyPrintJSONcontent(InputStream contentStream) throws IOException {
		/* Create the reader where to store the given response content and the mapper used for pretty printing */
    	BufferedReader bufReader = new BufferedReader(new InputStreamReader(contentStream));
    	ObjectMapper objMapper = new ObjectMapper();
		/* Read and write the given content under a pretty print format */
		String content = null;
		while ((content = bufReader.readLine()) != null) {
			Object stringObject = objMapper.readValue(content, Object.class);
			LOGGER.info("The content of the current response is:\n" + objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(stringObject));
		}
	}
	
	public static void main(String[] args) throws IOException {
		getAllTickets();
		getActualActiveTicket();
		createNewTicket();
		removeLastActiveTicket();
	}

}