package com.applaudostudios.interview.tokenstore;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.SerializationUtils;

public class SerializableObjectConverter {

	public static String serializeAuthentication(OAuth2Authentication authObject) {
		try {
			byte[] bytes = SerializationUtils.serialize(authObject);
			return Base64.encodeBase64String(bytes);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static OAuth2Authentication deserializeAuthentication(String serializedAuthObject) {
		try{
			byte[] bytes = Base64.decodeBase64(serializedAuthObject);
			return (OAuth2Authentication) SerializationUtils.deserialize(bytes);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
