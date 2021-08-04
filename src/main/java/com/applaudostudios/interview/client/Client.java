package com.applaudostudios.interview.client;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@Entity
@Table(name = "clients")
public class Client {
	
	@Id
	@Column(name = "client_id", nullable = false)
	@JsonProperty("clientId")
	@NotBlank
	private String clientId;
	
	@JsonProperty("clientSecret")
	@Column(name = "client_secret", nullable = false)
	@NotBlank
	private String clientSecret;
	
	@JsonProperty("clientName")
	@Column(name = "client_name", nullable = false)
	@NotBlank
	private String clientName;
	
	@JsonProperty("clientScopes")
	@Column(name = "client_scopes", nullable = false)
	@NotBlank
	private String clientScopes;
	
	@JsonProperty("clientRedirectUri")
	@Column(name = "client_redirect_uri", nullable = false)
	@NotBlank
	private String clientRedirectUri;
	
	@JsonProperty
	@Column(name = "client_token_expiration", nullable = false)
	@NotNull
	private Integer clientTokenExpiresInSeconds;
	
	@JsonProperty
	@Column(name = "refresh_token_expiration", nullable = false)
	@NotNull
	private Integer refreshTokenExpiresInSeconds;
	
	public Client() {
		super();
		this.clientId = UUID.randomUUID().toString();
	}

	public Client(String clientId, String clientSecret, String clientName, String clientScopes,
			Integer clientTokenExpiresInSeconds, Integer refreshTokenExpiresInSeconds) {
		super();
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.clientName = clientName;
		this.clientScopes = clientScopes;
		this.clientTokenExpiresInSeconds = clientTokenExpiresInSeconds;
		this.refreshTokenExpiresInSeconds = refreshTokenExpiresInSeconds;
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public Client clientName(String clientName) {
		this.setClientName(clientName);
		return this;
	}

	public String getClientScopes() {
		return clientScopes;
	}

	public void setClientScopes(String clientScopes) {
		this.clientScopes = clientScopes;
	}
	
	public Client clientScopes(String clientScopes) {
		this.setClientScopes(clientScopes);
		return this;
	}

	public Integer getClientTokenExpiresInSeconds() {
		return clientTokenExpiresInSeconds;
	}

	public void setClientTokenExpiresInSeconds(Integer clientTokenExpiresInSeconds) {
		this.clientTokenExpiresInSeconds = clientTokenExpiresInSeconds;
	}
	
	public Client clientTokenExpiresInSeconds(Integer clientTokenExpiresInSeconds) {
		this.setClientTokenExpiresInSeconds(clientTokenExpiresInSeconds);
		return this;
	}

	public Integer getRefreshTokenExpiresInSeconds() {
		return refreshTokenExpiresInSeconds;
	}

	public void setRefreshTokenExpiresInSeconds(Integer refreshTokenExpiresInSeconds) {
		this.refreshTokenExpiresInSeconds = refreshTokenExpiresInSeconds;
	}
	
	public Client refreshTokenExpiresInSeconds(Integer refreshTokenExpiresInSeconds) {
		this.setRefreshTokenExpiresInSeconds(refreshTokenExpiresInSeconds);
		return this;
	}

	public String getClientRedirectUri() {
		return clientRedirectUri;
	}

	public void setClientRedirectUri(String clientRedirectUri) {
		this.clientRedirectUri = clientRedirectUri;
	}
	
	public Client clientRedirectUri(String clientRedirectUri) {
		this.setClientRedirectUri(clientRedirectUri);
		return this;
	}
	
}
