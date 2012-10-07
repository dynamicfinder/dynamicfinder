package org.xsalefter.finder4j.jpa.handler;

import org.xsalefter.finder4j.spi.AbstractQueryBuilder;

/**
 * @author xsalefter (xsalefter@gmail.com)
 */
public class GreaterEqualRestrictionHandler extends SimpleComparatorRestrictionHandler {

	public GreaterEqualRestrictionHandler(final AbstractQueryBuilder queryBuilder) {
		super(queryBuilder, ">=");
	}

}