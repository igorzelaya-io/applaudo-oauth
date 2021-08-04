package com.applaudostudios.interview.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.applaudostudios.interview.client.ClientAffiliationService;
import com.applaudostudios.interview.tokenstore.SecurityTokenStore;
import com.applaudostudios.interview.user.UserService;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private ClientAffiliationService clientAffiliationService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserService userService;
		
	@Bean
	public TokenStore tokenStore() {
		return new SecurityTokenStore();
	}
	
	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()")
			.allowFormAuthenticationForClients()
			.passwordEncoder(encoder);
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer
			.withClientDetails(clientAffiliationService);
	}
	
	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpointsConfigurer) {
		endpointsConfigurer
					.tokenStore(tokenStore())
					.userDetailsService(userService)
					.authenticationManager(authenticationManager);
	}
	
}
