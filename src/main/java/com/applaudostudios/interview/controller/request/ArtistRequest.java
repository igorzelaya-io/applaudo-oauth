package com.applaudostudios.interview.controller.request;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "name")
public class ArtistRequest {
    private String name;
    
    public String getName() {
    	return this.name;
    }
}
