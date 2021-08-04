package com.applaudostudios.interview.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

public class ResourceNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(Class entityClass, String... searchParamsMap) {
		super(ResourceNotFoundException.generateExceptionMessage(entityClass.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
	}
	
	private static String generateExceptionMessage(String entity, Map<String, String> searchParameters) {
		return new StringBuilder(StringUtils.capitalize(entity))
							.append(" was not found for parameters ")
							.append(searchParameters).toString();
	}
	
	private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object... entries){
		if(entries.length % 2 != 0) throw new IllegalArgumentException("Invalid entries");
		
		return IntStream
				.range(0, entries.length / 2)
				.map(i -> i * 2)
                .collect(HashMap::new,
                		(m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])), Map::putAll);
	}

}
