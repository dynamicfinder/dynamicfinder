package org.xsalefter.finder4j.jpa.handler;

/**
 * @author xsalefter (xsalefter@gmail.com)
 */
public class GreaterThanRestrictionHandler extends SimpleComparatorRestrictionHandler {

	public GreaterThanRestrictionHandler(final String entityAliasName) {
		super(entityAliasName, ">");
	}

}
