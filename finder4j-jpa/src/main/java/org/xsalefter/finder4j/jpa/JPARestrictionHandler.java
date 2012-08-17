package org.xsalefter.finder4j.jpa;

import java.util.HashMap;

import org.xsalefter.finder4j.RestrictionHandler;
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

public class JPARestrictionHandler extends HashMap<RestrictionType, RestrictionHandler> {

	private static final long serialVersionUID = 1L;

	public JPARestrictionHandler() {
		super.put(RestrictionType.EQUAL, new EqualRestrictionHandler());
		super.put(RestrictionType.NOT_EQUAL, new NotEqualRestrictionHandler());
		super.put(RestrictionType.GREATER, new GreaterRestrictionHandler());
		super.put(RestrictionType.GREATER_EQUAL, new GreaterThanRestrictionHandler());
		super.put(RestrictionType.LESSER, new LesserRestrictionHandler());
		super.put(RestrictionType.LESSER_EQUAL, new LesserThanRestrictionHandler());
		super.put(RestrictionType.LIKE, new LikeRestrictionHandler());
		super.put(RestrictionType.LIKE_WITH_PREFIX, new LikeWithPrefixRestrictionHandler());
		super.put(RestrictionType.LIKE_WITH_POSTFIX, new LikeWithPostfixRestrictionHandler());
	}
}
