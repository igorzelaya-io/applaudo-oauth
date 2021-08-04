package com.applaudostudios.interview.client;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientAffiliationRepository extends CrudRepository<Client, String>{

	
}
