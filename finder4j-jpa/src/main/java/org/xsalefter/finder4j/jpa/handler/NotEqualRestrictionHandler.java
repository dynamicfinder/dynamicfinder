package org.xsalefter.finder4j.jpa.handler;

import org.xsalefter.finder4j.spi.AbstractQueryBuilder;

/**
 * @author xsalefter (xsalefter@gmail.com)
 */
public class NotEqualRestrictionHandler extends SimpleComparatorRestrictionHandler {

	public NotEqualRestrictionHandler(final AbstractQueryBuilder queryBuilder) {
		super(queryBuilder, "!=");
	}

}
