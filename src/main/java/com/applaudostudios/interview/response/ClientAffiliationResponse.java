package com.applaudostudios.interview.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ClientAffiliationResponse {
	
	@JsonProperty("clientId")
	private String clientId;
	
	@JsonProperty("clientSecret")
	private String clientSecret;
	
	public ClientAffiliationResponse() {
		super();
	}

	public ClientAffiliationResponse(String clientId, String clientSecret) {
		super();
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
}
