package com.applaudostudios.interview.client;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.applaudostudios.interview.request.ClientAffiliationRequest;
import com.applaudostudios.interview.response.BaseResponse;
import com.applaudostudios.interview.response.ClientAffiliationResponse;
import com.applaudostudios.interview.response.Response;

@RequestMapping(value = "/clients")
@RestController
public class ClientAffiliationController {

	@Autowired
	private ClientAffiliationService clientAffiliationSerice;
	
	@PostMapping
	public ResponseEntity<? extends Response<ClientAffiliationResponse>> postClient(@Valid @RequestBody(required = true)ClientAffiliationRequest clientRequest){
		ClientAffiliationResponse clientAffiliationResponse = this.clientAffiliationSerice.postNewClient(clientRequest);
		BaseResponse<ClientAffiliationResponse> clientResponse = new BaseResponse<>();
		return clientResponse.createResponse(HttpStatus.CREATED, "Client affiliated successfully", clientAffiliationResponse);
	}
}
