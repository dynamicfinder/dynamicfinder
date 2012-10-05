package org.xsalefter.finder4j.jpa.handler;

import org.xsalefter.finder4j.spi.AbstractQueryBuilder;

public class LikeWithPrefixRestrictionHandler extends LikeComparatorRestrictionHandler {

	private static final String PREFIX = "";
	private static final String POSTFIX = ",'%'";

	public LikeWithPrefixRestrictionHandler(final AbstractQueryBuilder queryBuilder) {
		super(queryBuilder, PREFIX, POSTFIX);
	}

}
