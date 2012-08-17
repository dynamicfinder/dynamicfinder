package org.xsalefter.finder4j.jpa.handler;

public class LikeWithPostfixRestrictionHandler extends LikeComparatorRestrictionHandler {

	private static final String PREFIX = "'%',";
	private static final String POSTFIX = "";

	public LikeWithPostfixRestrictionHandler() {
		super(PREFIX, POSTFIX);
	}

}
