package org.dynamicfinder.jpa.handler;

/**
 * @author xsalefter (xsalefter[at]gmail.com)
 */
public class LikeRestrictionHandler extends LikeComparatorRestrictionHandler {

	private static final String PREFIX = "'%', ";
	private static final String POSTFIX = ", '%'";

	public LikeRestrictionHandler() {
		super(PREFIX, POSTFIX);
	}

}
