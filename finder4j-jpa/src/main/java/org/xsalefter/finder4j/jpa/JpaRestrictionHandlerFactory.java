package org.xsalefter.finder4j.jpa;

import java.util.HashMap;

import org.xsalefter.finder4j.RestrictionType;
import org.xsalefter.finder4j.jpa.handler.EqualRestrictionHandler;
import org.xsalefter.finder4j.jpa.handler.GreaterRestrictionHandler;
import org.xsalefter.finder4j.jpa.handler.GreaterThanRestrictionHandler;
import org.xsalefter.finder4j.jpa.handler.LesserRestrictionHandler;
import org.xsalefter.finder4j.jpa.handler.LesserThanRestrictionHandler;
import org.xsalefter.finder4j.jpa.handler.LikeRestrictionHandler;
import org.xsalefter.finder4j.jpa.handler.LikeWithPostfixRestrictionHandler;
import org.xsalefter.finder4j.jpa.handler.LikeWithPrefixRestrictionHandler;
import org.xsalefter.finder4j.jpa.handler.NotEqualRestrictionHandler;
import org.xsalefter.finder4j.spi.AbstractQueryBuilder;
import org.xsalefter.finder4j.spi.RestrictionHandler;

/**
 * Put all classes under package <code>org.xsalefter.finder4j.jpa.handler</code>.
 * @author xsalefter (xsalefter@gmail.com)
 */
public class JpaRestrictionHandlerFactory extends HashMap<RestrictionType, RestrictionHandler> {

	private static final long serialVersionUID = 1L;

	public JpaRestrictionHandlerFactory(final AbstractQueryBuilder queryBuilder) {
		super.put(RestrictionType.EQUAL, new EqualRestrictionHandler(queryBuilder));
		super.put(RestrictionType.NOT_EQUAL, new NotEqualRestrictionHandler(queryBuilder));
		super.put(RestrictionType.GREATER, new GreaterRestrictionHandler(queryBuilder));
		super.put(RestrictionType.GREATER_EQUAL, new GreaterThanRestrictionHandler(queryBuilder));
		super.put(RestrictionType.LESSER, new LesserRestrictionHandler(queryBuilder));
		super.put(RestrictionType.LESSER_EQUAL, new LesserThanRestrictionHandler(queryBuilder));
		super.put(RestrictionType.LIKE, new LikeRestrictionHandler(queryBuilder));
		super.put(RestrictionType.LIKE_WITH_PREFIX, new LikeWithPrefixRestrictionHandler(queryBuilder));
		super.put(RestrictionType.LIKE_WITH_POSTFIX, new LikeWithPostfixRestrictionHandler(queryBuilder));
	}
}
