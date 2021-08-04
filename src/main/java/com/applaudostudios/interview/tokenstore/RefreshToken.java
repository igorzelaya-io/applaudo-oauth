package com.applaudostudios.interview.tokenstore;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "refresh_tokens")
@JsonSerialize
public class RefreshToken {

	@Id
	@Column(name = "id", nullable = false)
	@JsonProperty("id")
	private String id;
	
	@Column(name = "token_id", nullable = false)
	@JsonProperty("tokenId")
	private String tokenId;
	
	@Column(name = "token", nullable = false)
	@JsonProperty("token")
	private DefaultOAuth2RefreshToken token;
	
	@Column(name = "authentication", nullable = false)
	@JsonProperty("authentication")
	private String authentication;
	
	public RefreshToken() {
		super();	
		this.id = UUID.randomUUID().toString();
	}

	public RefreshToken(String tokenId, DefaultOAuth2RefreshToken token, String authentication) {
		super();
		this.tokenId = tokenId;
		this.token = token;
		this.authentication = authentication;
	}
	
	public OAuth2Authentication getAuthentication() {
		return SerializableObjectConverter.deserializeAuthentication(authentication);
	}
	
	public void setAuthentication(OAuth2Authentication authentication) {
		this.authentication = SerializableObjectConverter.serializeAuthentication(authentication);
	}
	
	public RefreshToken authentication(OAuth2Authentication authentication) {
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
	
	public RefreshToken tokenId(String tokenId) {
		this.setTokenId(tokenId);
		return this;
	}

	public DefaultOAuth2RefreshToken getToken() {
		return token;
	}

	public void setToken(DefaultOAuth2RefreshToken token) {
		this.token = token;
	}
	
	public RefreshToken token(DefaultOAuth2RefreshToken token) {
		this.setToken(token);
		return this;
	}
}
