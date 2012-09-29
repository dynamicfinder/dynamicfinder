package org.xsalefter.finder4j.jpa.handler;

public class LesserThanRestrictionHandler extends SimpleComparatorRestrictionHandler {

	public LesserThanRestrictionHandler(final String entityAliasName) {
		super(entityAliasName, "<=");
	}

}
