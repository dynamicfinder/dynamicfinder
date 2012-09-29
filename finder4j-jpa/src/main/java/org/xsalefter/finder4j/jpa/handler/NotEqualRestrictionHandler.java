package org.xsalefter.finder4j.jpa.handler;


public class NotEqualRestrictionHandler extends SimpleComparatorRestrictionHandler {

	public NotEqualRestrictionHandler(final String entityAliasName) {
		super(entityAliasName, "!=");
	}

}
