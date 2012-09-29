package org.xsalefter.finder4j.jpa.handler;

public class GreaterRestrictionHandler extends SimpleComparatorRestrictionHandler {

	public GreaterRestrictionHandler(final String entityAliasName) {
		super(entityAliasName, ">=");
	}

}
