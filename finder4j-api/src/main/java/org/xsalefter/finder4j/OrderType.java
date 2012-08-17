package org.xsalefter.finder4j;

public enum OrderType {
	ASC, DESC;
	
	public static final OrderType of(final String type) {
		if (type == null) 
			throw new NullPointerException("'type' parameter is null!");
		else if (!type.equalsIgnoreCase("ASC") || !type.equalsIgnoreCase("DESC")) 
			throw new IllegalArgumentException("'type' parameter only accept 'ASC' or 'DESC' value.");
		else 
			return OrderType.valueOf(type.toLowerCase());
	}
}
