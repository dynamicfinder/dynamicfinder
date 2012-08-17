package org.xsalefter.finder4j.spi;

import java.util.Map;

import org.xsalefter.finder4j.Restriction;

public class RestrictionHandlerDTO {

	private String restrictionString;
	private Map<String, Restriction> addedRestrictions;

	public RestrictionHandlerDTO(String restrictionString, Map<String, Restriction> restrictions) {
		this.restrictionString = restrictionString;
		this.addedRestrictions = restrictions;
	}

	public String getRestrictionString() {
		return restrictionString;
	}

	public Map<String, Restriction> getAddedRestrictions() {
		return addedRestrictions;
	}

}
