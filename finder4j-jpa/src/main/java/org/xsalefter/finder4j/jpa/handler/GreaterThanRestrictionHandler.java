package org.xsalefter.finder4j.jpa.handler;

public class GreaterThanRestrictionHandler extends SimpleComparatorRestrictionHandler {

	public GreaterThanRestrictionHandler(final String entityAliasName) {
		super(entityAliasName, ">");
	}

}
