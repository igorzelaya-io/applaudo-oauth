package com.applaudostudios.interview.tokenstore;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String>{

	Optional<RefreshToken> findByTokenId(String tokenId);
	
	
	
}
