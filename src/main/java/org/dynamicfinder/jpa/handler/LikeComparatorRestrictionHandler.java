package org.dynamicfinder.jpa.handler;

import org.dynamicfinder.Nullable;
import org.dynamicfinder.Restriction;
import org.dynamicfinder.spi.AbstractQueryBuilder;
import org.dynamicfinder.spi.RestrictionHandler;


/**
 * Kind of {@link RestrictionHandler} for {@link RestrictionType#LIKE}, 
 * {@link RestrictionType#LIKE_WITH_POSTFIX}, {@link RestrictionType#LIKE_WITH_PREFIX}.
 * @author xsalefter (xsalefter[at]gmail.com)
 */
class LikeComparatorRestrictionHandler implements RestrictionHandler {

	private String prefix;
	private String postfix;

	public LikeComparatorRestrictionHandler(
		final String prefix, 
		final String postfix) {

		super();
		this.prefix = prefix;
		this.postfix = postfix;
	}

	@Override
	public Result handleRestriction(
			final AbstractQueryBuilder abstractQueryBuilder, 
			final Restriction restriction) {
		final StringBuilder builder = new StringBuilder();
		final Nullable nullable = restriction.getNullable();

		final String restrictionId = " ?" + restriction.getParameter();
		final String fieldToRestrict = restriction.getField();
		final String entityAndField = abstractQueryBuilder.getEntityAliasName() + 
				"." + fieldToRestrict;
		final Object restrictionValue = restriction.getValue();

		switch (nullable) {
			case DISCARD:
				boolean handleRestriction = false;
				if (restrictionValue instanceof String) {
					final String strValue = ((String) restrictionValue);
					handleRestriction = (!strValue.isEmpty());
				} else handleRestriction = (restrictionValue != null);

				if (handleRestriction) {
					builder.append(entityAndField).
						append(" like concat(").
						append(this.prefix).
						append(restrictionId).
						append(this.postfix).
						append(")");
					return new Result(builder.toString(), true);
				} else {
					return new Result(builder.toString(), false);
				}

			case KEEP:
				if (restrictionValue != null) {
					builder.append(entityAndField).
						append(" like concat(").
						append(this.prefix).
						append(restrictionId).
						append(this.postfix).append(")");

					return new Result(builder.toString(), true);
				} else {
					throw new IllegalStateException("'like' restriction doesn't support null value.");
				}

			default:
				throw new IllegalStateException("Unknown Nullable value.");
		}
	}

}
