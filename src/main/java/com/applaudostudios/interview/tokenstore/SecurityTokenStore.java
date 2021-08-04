package com.applaudostudios.interview.tokenstore;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.applaudostudios.interview.exception.ResourceNotFoundException;


public class SecurityTokenStore implements TokenStore{

	@Autowired
	private AccessTokenRepository accessTokenRepo;
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();
	
	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		return readAuthentication(token.getValue());
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		AccessToken accessToken = accessTokenRepo.findByTokenId(extractTokenKey(token))
												 .orElseThrow(() -> new ResourceNotFoundException(AccessToken.class, "tokenId", extractTokenKey(token)));
		return accessToken.getAuthentication();
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		String refreshToken = null;
		if(token.getRefreshToken() != null) {
			refreshToken = token.getRefreshToken().getValue();
		}
		
		if(readAccessToken(token.getValue()) != null) {
			this.removeAccessToken(token);
		}
		DefaultOAuth2AccessToken defaultToken = new DefaultOAuth2AccessToken(token);
		AccessToken appToken = new AccessToken();
		appToken
				.tokenId(extractTokenKey(token.getValue()))
				.token(defaultToken)
				.authenticationId(authenticationKeyGenerator.extractKey(authentication))
				.username(authentication.isClientOnly() ? null : authentication.getName())
				.clientId(authentication.getOAuth2Request().getClientId())
				.authentication(authentication)
				.refreshToken(extractTokenKey(refreshToken));
		this.accessTokenRepo.save(appToken);
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		AccessToken accessToken = this.accessTokenRepo.findByTokenId(extractTokenKey(tokenValue))
														.orElseThrow(() -> new ResourceNotFoundException(AccessToken.class, "tokenId", extractTokenKey(tokenValue)));
		return accessToken.getToken();
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		AccessToken accessToken = this.accessTokenRepo.findByTokenId(extractTokenKey(token.getValue()))
				.orElseThrow(() -> new ResourceNotFoundException(AccessToken.class, "tokenId", extractTokenKey(token.getValue()))); 
		this.accessTokenRepo.delete(accessToken);
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		RefreshToken token = new RefreshToken();
		DefaultOAuth2RefreshToken defaultToken = new DefaultOAuth2RefreshToken(refreshToken.getValue());
		token
			.tokenId(extractTokenKey(refreshToken.getValue()))
			.token(defaultToken)
			.authentication(authentication);
		this.refreshTokenRepository.save(token);
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		RefreshToken refreshToken = this.refreshTokenRepository.findByTokenId(extractTokenKey(tokenValue))
																.orElseThrow(() -> new ResourceNotFoundException(RefreshToken.class, "tokenId", extractTokenKey(tokenValue)));
		return refreshToken.getToken();
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		RefreshToken refreshToken = this.refreshTokenRepository.findByTokenId(extractTokenKey(token.getValue()))
				.orElseThrow(() -> new ResourceNotFoundException(RefreshToken.class, "tokenId", extractTokenKey(token.getValue())));
		return refreshToken.getAuthentication();
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		RefreshToken refreshToken = this.refreshTokenRepository.findByTokenId(extractTokenKey(token.getValue()))
				.orElseThrow(() -> new ResourceNotFoundException(RefreshToken.class, "tokenId", extractTokenKey(token.getValue())));
		this.refreshTokenRepository.delete(refreshToken);
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		AccessToken token = this.accessTokenRepo.findByRefreshToken(extractTokenKey(refreshToken.getValue()))
												.orElseThrow(() -> new ResourceNotFoundException(AccessToken.class, "refreshToken", extractTokenKey(refreshToken.getValue())));
		this.accessTokenRepo.delete(token);
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		OAuth2AccessToken accessToken = null;
		String authenticationId = authenticationKeyGenerator.extractKey(authentication);
		AccessToken token = this.accessTokenRepo.findByAuthenticationId(authenticationId)
												.orElseThrow(() -> new ResourceNotFoundException(AccessToken.class, "authenticationid", authenticationId));
		accessToken = token.getToken();
		if(accessToken != null && !authenticationId.equals(this.authenticationKeyGenerator.extractKey(this.readAuthentication(accessToken)))) {
			this.removeAccessToken(accessToken);
			this.storeAccessToken(accessToken, authentication);
		}
		return accessToken;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		Collection<OAuth2AccessToken> tokens = new ArrayList<>();
		List<AccessToken> result = this.accessTokenRepo.findByClientIdAndUsername(clientId, userName);
		result
			.stream()
			.map(token -> token.getToken())
			.forEach(token -> tokens.add(token));
		return tokens;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		Collection<OAuth2AccessToken> tokens = new ArrayList<>();
		List<AccessToken> result = this.accessTokenRepo.findByClientId(clientId);
		result
			.stream()
			.map(token -> token.getToken())
			.forEach(token -> tokens.add(token));
		return tokens;
	}
	
	private String extractTokenKey(String token) {
		if(token == null) {
			return null;
		}
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		}
		catch(NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 algorithm not available. Fatal (should be in the JDK).");
		}
		
		try {
			byte[] bytes = digest.digest(token.getBytes("UTF-8"));
			return String.format("%032x", new Object[] {new BigInteger(1, bytes)});
		}
		catch(UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
		}
	}

}
