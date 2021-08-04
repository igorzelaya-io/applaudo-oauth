package com.applaudostudios.interview.response;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Class used to map a BaseResponse
 * @author igorzelaya
 *
 * @param <T>
 */
@JsonSerialize
public class BaseResponse<T> implements Response<T> {

	@JsonProperty("responseDate")
	private Instant responseDate;
	
	@JsonProperty("responseHttpStatusCode")
	private Integer responseHttpStatusCode;
	
	@JsonProperty("responseMessage")
	private String responseMessage;
	
	@JsonProperty("responsePayload")
	private T responsePayload;
	
	public BaseResponse() {
		super();
	}

	public BaseResponse(Instant responseDate, Integer responseHttpStatusCode, String responseMessage,
			T responsePayload) {
		super();
		this.responseDate = responseDate;
		this.responseHttpStatusCode = responseHttpStatusCode;
		this.responseMessage = responseMessage;
		this.responsePayload = responsePayload;
	}

	@Override
	public Instant getResponseDate() {
		return this.responseDate;
	}

	@Override
	public void setResponseDate(Instant date) {
		this.responseDate = date;		
	}

	@Override
	public Integer getResponseHttpStatusCode() {
		return this.responseHttpStatusCode;
	}

	@Override
	public void setResponseHttpStatusCode(Integer httpStatusCode) {
		this.responseHttpStatusCode = httpStatusCode;
	}

	@Override
	public String getResponseMessage() {
		return this.responseMessage;
	}

	@Override
	public void setResponseMessage(String message) {
		this.responseMessage = message;
	}

	@Override
	public T getResponsePayload() {
		return this.responsePayload;
	}

	@Override
	public void setResponsePayload(T payload) {
		this.responsePayload = payload;
	}
}
