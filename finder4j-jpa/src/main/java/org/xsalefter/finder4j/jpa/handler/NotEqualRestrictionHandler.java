package org.xsalefter.finder4j.jpa.handler;

/**
 * @author xsalefter (xsalefter@gmail.com)
 */
public class NotEqualRestrictionHandler extends SimpleComparatorRestrictionHandler {

	public NotEqualRestrictionHandler(final String entityAliasName) {
		super(entityAliasName, "!=");
	}

}
