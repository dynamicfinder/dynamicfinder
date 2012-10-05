package org.xsalefter.finder4j.jpa.handler;

import org.xsalefter.finder4j.spi.AbstractQueryBuilder;

/**
 * @author xsalefter (xsalefter@gmail.com)
 */
public class LikeRestrictionHandler extends LikeComparatorRestrictionHandler {

	private static final String PREFIX = "'%',";
	private static final String POSTFIX = ",'%'";

	public LikeRestrictionHandler(final AbstractQueryBuilder queryBuilder) {
		super(queryBuilder, PREFIX, POSTFIX);
	}

}
