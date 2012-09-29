package org.xsalefter.finder4j.jpa.handler;


/**
 * @author xsalefter (xsalefter@gmail.com)
 */
public class EqualRestrictionHandler extends SimpleComparatorRestrictionHandler {

	public EqualRestrictionHandler(final String entityAliasName) {
		super(entityAliasName, "=");
	}

}
