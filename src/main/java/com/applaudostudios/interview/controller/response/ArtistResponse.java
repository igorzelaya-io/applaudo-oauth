package com.applaudostudios.interview.controller.response;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "name")
public class ArtistResponse {
    private String name;
    
    public ArtistResponse(String name) {
    	super();
    	this.name = name;
    }
    
    public String getName() {
    	return this.name;
    }

}
