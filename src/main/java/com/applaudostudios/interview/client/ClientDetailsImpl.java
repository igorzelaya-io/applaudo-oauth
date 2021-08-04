package com.applaudostudios.interview.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import com.applaudostudios.interview.role.ERole;
import com.applaudostudios.interview.role.Role;

public class ClientDetailsImpl implements ClientDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String clientId;
	
	private Set<String> resourceIds = new HashSet<>();
	
	private String clientSecret;
	
	private Set<String> scopes = new HashSet<>();
	
	private Set<String> authorizedGrantTypes = new HashSet<>();
	
	private Set<String> registeredRedirectUris = new HashSet<>();
	
	private Collection<? extends GrantedAuthority> authorities = new HashSet<>();
	
	private Integer accessTokenValiditySeconds;
	
	private Integer refereshTokenValiditySeconds;
	
	private Map<String, Object> additionalInformation = new HashMap<>();
	
	
	
	public ClientDetailsImpl(String clientId, Set<String> resourceIds, String clientSecret, Set<String> scopes,
			Set<String> authorizedGrantTypes, Set<String> registeredRedirectUris,
			Collection<? extends GrantedAuthority> authorities, Integer accessTokenValiditySeconds,
			Integer refereshTokenValiditySeconds, Map<String, Object> additionalInformation) {
		super();
		this.clientId = clientId;
		this.resourceIds = resourceIds;
		this.clientSecret = clientSecret;
		this.scopes = scopes;
		this.authorizedGrantTypes = authorizedGrantTypes;
		this.registeredRedirectUris = registeredRedirectUris;
		this.authorities = authorities;
		this.accessTokenValiditySeconds = accessTokenValiditySeconds;
		this.refereshTokenValiditySeconds = refereshTokenValiditySeconds;
		this.additionalInformation = additionalInformation;
	}
	
	public static ClientDetailsImpl buildFromClient(@Valid Client client) {
		final String clientId = client.getClientId();
		final Set<String> resourceIds = Set.of("api");
		final String clientSecret = client.getClientSecret();
		final Set<String> scopes = Set.of(client.getClientScopes().split(" "));
		final Set<String> authorizedGrantTypes = Set.of("password", "refresh_token");
		final Set<String> registeredRedirectUris = Set.of(client.getClientRedirectUri());
		final List<Role> roleAuthorities = List.of(new Role( ERole.USER.toString() ), new Role( ERole.ADMIN.toString() ));
		final Set<GrantedAuthority> authorities = roleAuthorities
												.stream()
												.map(role -> new SimpleGrantedAuthority(role.getAuthority()))
												.collect(Collectors.toSet());
		return new ClientDetailsImpl(clientId, resourceIds, clientSecret, scopes, authorizedGrantTypes, 
									 registeredRedirectUris, authorities, client.getClientTokenExpiresInSeconds(),
									 client.getRefreshTokenExpiresInSeconds(), new HashMap<>());
	}

	@Override
	public String getClientId() {
		return this.clientId;
	}

	@Override
	public Set<String> getResourceIds() {
		return this.resourceIds;
	}

	@Override
	public boolean isSecretRequired() {
		return true;
	}

	@Override
	public String getClientSecret() {
		return this.clientSecret;
	}

	@Override
	public boolean isScoped() {
		return false;
	}

	@Override
	public Set<String> getScope() {
		return this.scopes; 
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return this.authorizedGrantTypes;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return this.registeredRedirectUris;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return (Collection<GrantedAuthority>) this.authorities;
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return this.accessTokenValiditySeconds;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return this.refereshTokenValiditySeconds;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return false;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return this.additionalInformation;
	}
	
	@Override
	public boolean equals(Object object) {
		if(this == object) return true;
		if(object == null || getClass() != object.getClass()) return false;
		
		ClientDetailsImpl client = (ClientDetailsImpl) object;
		return Objects.equals(this.clientId, client.clientId);
	}

}
