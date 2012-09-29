package org.xsalefter.finder4j.jpa.handler;

/**
 * @author xsalefter (xsalefter@gmail.com)
 */
public class LesserRestrictionHandler extends SimpleComparatorRestrictionHandler {

	public LesserRestrictionHandler(final String entityAliasName) {
		super(entityAliasName, "<");
	}

}
