package com.applaudostudios.interview.tokenstore;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "access_tokens")
@JsonSerialize
public class AccessToken {
	
	@Id
	@JsonProperty("id")
	@Column(name = "id", nullable = false)
	private String id;
	
	@JsonProperty("tokenId")
	@Column(name = "token_id", nullable = false)
	private String tokenId;
	
	@Column(name = "token", nullable = false)
	@JsonProperty("token")
	private DefaultOAuth2AccessToken token;
	
	@JsonProperty("authenticationId")
	@Column(name = "authentication_id", nullable = false)
	private String authenticationId;
	
	@JsonProperty("username")
	@Column(name = "user_name")
	private String username;
	
	@JsonProperty("clientId")
	@Column(name = "client_id")
	private String clientId; 
	
	@JsonProperty("authentication")
	@Column(name = "authentication", nullable = false)
	private String authentication;
	
	@JsonProperty("refreshToken")
	@Column(name = "refresh_token")
	private String refreshToken;
	
	public AccessToken() {
		super();
		this.id = UUID.randomUUID().toString();
	}

	public AccessToken(String tokenId, DefaultOAuth2AccessToken token, String authenticationId, String username,
			String clientId, String authentication, String refreshToken) {
		super();
		this.tokenId = tokenId;
		this.token = token;
		this.authenticationId = authenticationId;
		this.username = username;
		this.clientId = clientId;
		this.authentication = authentication;
		this.refreshToken = refreshToken;
	}

	public OAuth2Authentication getAuthentication() {
		return SerializableObjectConverter.deserializeAuthentication(authentication);
	}
	
	public void setAuthentication(OAuth2Authentication authentication) {
		this.authentication = SerializableObjectConverter.serializeAuthentication(authentication);
	}
	
	public AccessToken authentication(OAuth2Authentication authentication) {
		this.setAuthentication(authentication);
		return this;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	
	public AccessToken tokenId(String tokenId) {
		this.setTokenId(tokenId);
		return this;
	}

	public DefaultOAuth2AccessToken getToken() {
		return token;
	}

	public void setToken(DefaultOAuth2AccessToken token) {
		this.token = token;
	}
	
	public AccessToken token(DefaultOAuth2AccessToken token) {
		this.setToken(token);
		return this;
	}

	public String getAuthenticationId() {
		return authenticationId;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}
	
	public AccessToken authenticationId(String authenticationId) {
		this.setAuthenticationId(authenticationId);
		return this;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public AccessToken username(String username) {
		this.setUsername(username);
		return this;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public AccessToken clientId(String clientId) {
		this.setClientId(clientId);
		return this;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public AccessToken refreshToken(String refreshToken) {
		this.setRefreshToken(refreshToken);
		return this;
	}
	
}
