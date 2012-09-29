package org.xsalefter.finder4j.jpa.handler;

public class LikeRestrictionHandler extends LikeComparatorRestrictionHandler {

	private static final String PREFIX = "'%',";
	private static final String POSTFIX = ",'%'";

	public LikeRestrictionHandler() {
		super(PREFIX, POSTFIX);
	}

}