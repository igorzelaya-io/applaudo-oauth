package com.applaudostudios.interview.tokenstore;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenRepository extends CrudRepository<AccessToken, String>{
	
	List<AccessToken> findByClientId(String clientId);
	
	List<AccessToken> findByClientIdAndUsername(String clientId, String username);
	
	Optional<AccessToken> findByTokenId(String tokenId);
	
	Optional<AccessToken> findByRefreshToken(String refreshToken);
	
	Optional<AccessToken> findByAuthenticationId(String authenticationId);

}
