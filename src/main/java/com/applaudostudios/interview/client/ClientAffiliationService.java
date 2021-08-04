package com.applaudostudios.interview.client;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import com.applaudostudios.interview.exception.ResourceNotFoundException;
import com.applaudostudios.interview.request.ClientAffiliationRequest;
import com.applaudostudios.interview.response.ClientAffiliationResponse;

@Service
public class ClientAffiliationService implements ClientDetailsService{

	@Autowired
	private ClientAffiliationRepository clientAffiliationRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		Client retrievedClient = this.clientAffiliationRepo.findById(clientId)
																.orElseThrow(() -> new ResourceNotFoundException(Client.class, "id", clientId));
		ClientDetailsImpl clientDetailsCustomImpl = ClientDetailsImpl.buildFromClient(retrievedClient);
		return clientDetailsCustomImpl;
		
	}
	
	public ClientAffiliationResponse postNewClient(ClientAffiliationRequest client) {
		Client clientToSave = new Client();
		clientToSave.clientName(client.getName())
					.clientScopes(client.getScopes())
					.clientRedirectUri(client.getRedirectUri())
					.clientTokenExpiresInSeconds(client.getAccessTokenExpiresInSeconds())
					.refreshTokenExpiresInSeconds(client.getRefereshTokenExpiresInSeconds())
					.setClientSecret(encoder.encode(UUID.randomUUID().toString()));
		
		this.clientAffiliationRepo.save(clientToSave);
		ClientAffiliationResponse clientResponse = new ClientAffiliationResponse(clientToSave.getClientId(), clientToSave.getClientSecret());
		return clientResponse;
	}
	
	
}
