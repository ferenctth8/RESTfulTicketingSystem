package com.feritoth.ticketingsystem.core;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.feritoth.ticketingsystem.utils.LocalDateTimeDeserializer;
import com.feritoth.ticketingsystem.utils.LocalDateTimeSerializer;

/**
 * The core class of the ticketing system.
 * 
 * @author Ferenc Toth
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClientTicket implements Serializable {
	
	//Serialization ID
	private static final long serialVersionUID = -701532337075740208L;
	
	//Attribute list
	private Integer serialNumber;//To je pořadové číslo.
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime arrivalDateTime;//To je vstupní datum a čas.
	private Integer waitingNumber;//To je čekající číslo.
	
	//Default constructor
	public ClientTicket() {
		super();
	}
	
	//Parameterized class constructor
	public ClientTicket(Integer serialNumber, LocalDateTime arrivalDateTime, Integer waitingNumber) {
		super();
		this.serialNumber = serialNumber;
		this.arrivalDateTime = arrivalDateTime;
		this.waitingNumber = waitingNumber;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public LocalDateTime getArrivalDateTime() {
		return arrivalDateTime;
	}

	public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}

	public Integer getWaitingNumber() {
		return waitingNumber;
	}

	public void setWaitingNumber(Integer waitingNumber) {
		this.waitingNumber = waitingNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		//Format the arrival date and time to correspond to the input file content
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    	String formattedDateTime = arrivalDateTime.format(dtf);
		return "ClientTicket [serialNumber=" + serialNumber + ", arrivalDateTime=" + formattedDateTime + ", waitingNumber=" + waitingNumber + "]";
	}

}