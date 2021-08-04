package com.applaudostudios.interview.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class ClientAffiliationRequest {

	@JsonProperty("name")
	@NotBlank
	private String name;
	
	@JsonProperty("scopes")
	@NotBlank
	private String scopes;
	
	@JsonProperty("redirectUri")
	@NotBlank
	private String redirectUri;
	
	@JsonProperty("accessTokenExpiresInSeconds")
	@NotNull
	private Integer accessTokenExpiresInSeconds;
	
	@JsonProperty("refreshTokenExpiresInSeconds")
	@NotNull
	private Integer refreshTokenExpiresInSeconds; 	

	public ClientAffiliationRequest() {
		super();
	}

	public ClientAffiliationRequest(String name, String scopes, String redirectUri, Integer accessTokenExpiresInSeconds,
			Integer refreshTokenExpiresInSeconds) {
		super();
		this.name = name;
		this.scopes = scopes;
		this.redirectUri = redirectUri;
		this.accessTokenExpiresInSeconds = accessTokenExpiresInSeconds;
		this.refreshTokenExpiresInSeconds = refreshTokenExpiresInSeconds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScopes() {
		return scopes;
	}

	public void setScopes(String scopes) {
		this.scopes = scopes;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public Integer getAccessTokenExpiresInSeconds() {
		return accessTokenExpiresInSeconds;
	}

	public void setAccessTokenExpiresInSeconds(Integer accessTokenExpiresInSeconds) {
		this.accessTokenExpiresInSeconds = accessTokenExpiresInSeconds;
	}

	public Integer getRefereshTokenExpiresInSeconds() {
		return refreshTokenExpiresInSeconds;
	}

	public void setRefereshTokenExpiresInSeconds(Integer refereshTokenExpiresInSeconds) {
		this.refreshTokenExpiresInSeconds = refereshTokenExpiresInSeconds;
	}

}
