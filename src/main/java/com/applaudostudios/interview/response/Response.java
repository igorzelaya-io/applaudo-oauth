package com.applaudostudios.interview.response;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Interface for mapping all responses
 * @author igorzelaya
 *
 * @param <T>
 */
public interface Response<T>{

	public Instant getResponseDate();
	public void setResponseDate(Instant responsedate);
	
	public Integer getResponseHttpStatusCode();
	public void setResponseHttpStatusCode(Integer httpStatusCode);
	
	public String getResponseMessage();
	public void setResponseMessage(String message);
	
	public T getResponsePayload();
	public void setResponsePayload(T payload);
	
	/**
	 * Method used to build responses with given values.
	 * @param httpStatus
	 * @param message
	 * @param payload
	 * @return ResponseEntity
	 */
	public default ResponseEntity<? extends Response<T>> createResponse(HttpStatus responseHttpStatus, 
																		String responseMessage, T responsePayload){
		this.setResponseDate(Instant.now());
		this.setResponseHttpStatusCode(responseHttpStatus.value());
		this.setResponseMessage(responseMessage);
		this.setResponsePayload(responsePayload);
		return new ResponseEntity<Response<T>>(this, responseHttpStatus);
	}
	
}
