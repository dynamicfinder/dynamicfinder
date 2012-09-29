package org.xsalefter.finder4j.jpa.handler;

/**
 * @author xsalefter (xsalefter@gmail.com)
 */
public class LesserThanRestrictionHandler extends SimpleComparatorRestrictionHandler {

	public LesserThanRestrictionHandler(final String entityAliasName) {
		super(entityAliasName, "<=");
	}

}
