package org.xsalefter.finder4j.jpa.handler;

import org.xsalefter.finder4j.Nullable;
import org.xsalefter.finder4j.Restriction;
import org.xsalefter.finder4j.RestrictionType;
import org.xsalefter.finder4j.spi.AbstractQueryBuilder;
import org.xsalefter.finder4j.spi.RestrictionHandler;

/**
 * Kind of {@link RestrictionHandler} for {@link RestrictionType#LIKE}, 
 * {@link RestrictionType#LIKE_WITH_POSTFIX}, {@link RestrictionType#LIKE_WITH_PREFIX}.
 * @author xsalefter (xsalefter@gmail.com)
 */
class LikeComparatorRestrictionHandler extends RestrictionHandler {

	private String prefix;
	private String postfix;

	public LikeComparatorRestrictionHandler(
		final AbstractQueryBuilder queryBuilder, 
		final String prefix, 
		final String postfix) {

		super(queryBuilder);
		this.prefix = prefix;
		this.postfix = postfix;
	}

	@Override
	public DTO handleRestriction(Restriction restriction) {
		final StringBuilder builder = new StringBuilder();
		final Nullable nullable = restriction.getNullable();

		final String restrictionId = " ?" + restriction.getParameter();
		final String fieldToRestrict = restriction.getField();
		final String entityAndField = super.getEntityAliasName() + "." + fieldToRestrict;
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
					return new DTO(builder.toString(), true);
				} else {
					return new DTO(builder.toString(), false);
				}

			case KEEP:
				if (restrictionValue != null) {
					builder.append(entityAndField).
						append(" like concat(").
						append(this.prefix).
						append(restrictionId).
						append(this.postfix).append(")");

					return new DTO(builder.toString(), true);
				} else {
					throw new IllegalStateException("'like' restriction doesn't support null value.");
				}

			default:
				throw new IllegalStateException("Unknown Nullable value.");
		}
	}

}
