package org.dynamicfinder.jpa;

import java.util.HashMap;

import org.dynamicfinder.RestrictionType;
import org.dynamicfinder.jpa.handler.EqualRestrictionHandler;
import org.dynamicfinder.jpa.handler.GreaterEqualRestrictionHandler;
import org.dynamicfinder.jpa.handler.GreaterRestrictionHandler;
import org.dynamicfinder.jpa.handler.LesserEqualRestrictionHandler;
import org.dynamicfinder.jpa.handler.LesserRestrictionHandler;
import org.dynamicfinder.jpa.handler.LikeRestrictionHandler;
import org.dynamicfinder.jpa.handler.LikeWithPostfixRestrictionHandler;
import org.dynamicfinder.jpa.handler.LikeWithPrefixRestrictionHandler;
import org.dynamicfinder.jpa.handler.NotEqualRestrictionHandler;
import org.dynamicfinder.spi.RestrictionHandler;


/**
 * Put all classes under package <code>org.xsalefter.finder4j.jpa.handler</code>.
 * @author xsalefter (xsalefter[at]gmail.com)
 */
public class JpaRestrictionHandlerFactory extends HashMap<RestrictionType, RestrictionHandler> {

	private static final long serialVersionUID = 1L;

	public JpaRestrictionHandlerFactory() {
		super.put(RestrictionType.EQUAL, new EqualRestrictionHandler());
		super.put(RestrictionType.NOT_EQUAL, new NotEqualRestrictionHandler());
		super.put(RestrictionType.GREATER, new GreaterRestrictionHandler());
		super.put(RestrictionType.GREATER_EQUAL, new GreaterEqualRestrictionHandler());
		super.put(RestrictionType.LESSER, new LesserRestrictionHandler());
		super.put(RestrictionType.LESSER_EQUAL, new LesserEqualRestrictionHandler());
		super.put(RestrictionType.LIKE, new LikeRestrictionHandler());
		super.put(RestrictionType.LIKE_WITH_PREFIX, new LikeWithPrefixRestrictionHandler());
		super.put(RestrictionType.LIKE_WITH_POSTFIX, new LikeWithPostfixRestrictionHandler());
	}
}
