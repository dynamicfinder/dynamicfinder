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
import org.xsalefter.finder4j.spi.RestrictionHandler;

/**
 * Put all classes under package <code>org.xsalefter.finder4j.jpa.handler</code>.
 * @author xsalefter (xsalefter@gmail.com)
 */
public class JPARestrictionHandlerFactory extends HashMap<RestrictionType, RestrictionHandler> {

	private static final long serialVersionUID = 1L;

	public JPARestrictionHandlerFactory(final String entityAliasName) {
		super.put(RestrictionType.EQUAL, new EqualRestrictionHandler(entityAliasName));
		super.put(RestrictionType.NOT_EQUAL, new NotEqualRestrictionHandler(entityAliasName));
		super.put(RestrictionType.GREATER, new GreaterRestrictionHandler(entityAliasName));
		super.put(RestrictionType.GREATER_EQUAL, new GreaterThanRestrictionHandler(entityAliasName));
		super.put(RestrictionType.LESSER, new LesserRestrictionHandler(entityAliasName));
		super.put(RestrictionType.LESSER_EQUAL, new LesserThanRestrictionHandler(entityAliasName));
		super.put(RestrictionType.LIKE, new LikeRestrictionHandler(entityAliasName));
		super.put(RestrictionType.LIKE_WITH_PREFIX, new LikeWithPrefixRestrictionHandler(entityAliasName));
		super.put(RestrictionType.LIKE_WITH_POSTFIX, new LikeWithPostfixRestrictionHandler(entityAliasName));
	}
}
