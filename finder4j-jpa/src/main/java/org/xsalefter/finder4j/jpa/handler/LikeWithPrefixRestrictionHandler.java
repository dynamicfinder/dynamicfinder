package org.xsalefter.finder4j.jpa.handler;

public class LikeWithPrefixRestrictionHandler extends LikeComparatorRestrictionHandler {

	private static final String PREFIX = "";
	private static final String POSTFIX = ",'%'";

	public LikeWithPrefixRestrictionHandler() {
		super(PREFIX, POSTFIX);
	}

}
