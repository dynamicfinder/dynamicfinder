package org.xsalefter.finder4j.jpa.handler;

/**
 * @author xsalefter (xsalefter@gmail.com)
 */
public class GreaterRestrictionHandler extends SimpleComparatorRestrictionHandler {

	public GreaterRestrictionHandler(final String entityAliasName) {
		super(entityAliasName, ">=");
	}

}
