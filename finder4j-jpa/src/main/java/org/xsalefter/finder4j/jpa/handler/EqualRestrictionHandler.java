package org.xsalefter.finder4j.jpa.handler;

public class EqualRestrictionHandler extends SimpleComparatorRestrictionHandler {

	public EqualRestrictionHandler(final String entityAliasName) {
		super(entityAliasName, "=");
	}

}
