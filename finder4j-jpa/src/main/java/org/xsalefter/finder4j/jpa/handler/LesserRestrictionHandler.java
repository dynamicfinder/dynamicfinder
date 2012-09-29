package org.xsalefter.finder4j.jpa.handler;

public class LesserRestrictionHandler extends SimpleComparatorRestrictionHandler {

	public LesserRestrictionHandler(final String entityAliasName) {
		super(entityAliasName, "<");
	}

}
