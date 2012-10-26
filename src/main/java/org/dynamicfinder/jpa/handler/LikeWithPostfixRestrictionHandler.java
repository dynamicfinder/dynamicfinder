package org.dynamicfinder.jpa.handler;

/**
 * @author xsalefter (xsalefter[at]gmail.com)
 */
public class LikeWithPostfixRestrictionHandler extends LikeComparatorRestrictionHandler {

	private static final String PREFIX = "'%', ";
	private static final String POSTFIX = "";

	public LikeWithPostfixRestrictionHandler() {
		super(PREFIX, POSTFIX);
	}

}
