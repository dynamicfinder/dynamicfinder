package org.xsalefter.finder4j.jpa.handler;

import org.xsalefter.finder4j.spi.AbstractQueryBuilder;

/**
 * @author xsalefter (xsalefter@gmail.com)
 */
public class LikeWithPostfixRestrictionHandler extends LikeComparatorRestrictionHandler {

	private static final String PREFIX = "'%',";
	private static final String POSTFIX = "";

	public LikeWithPostfixRestrictionHandler(final AbstractQueryBuilder queryBuilder) {
		super(queryBuilder, PREFIX, POSTFIX);
	}

}
