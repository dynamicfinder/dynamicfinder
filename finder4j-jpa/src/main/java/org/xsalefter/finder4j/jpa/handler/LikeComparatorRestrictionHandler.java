package org.xsalefter.finder4j.jpa.handler;

import org.xsalefter.finder4j.Nullable;
import org.xsalefter.finder4j.Restriction;
import org.xsalefter.finder4j.spi.RestrictionHandler;

class LikeComparatorRestrictionHandler extends RestrictionHandler {

	private String prefix;
	private String postfix;

	public LikeComparatorRestrictionHandler(final String entityAliasName, String prefix, String postfix) {
		super(entityAliasName);
		this.prefix = prefix;
		this.postfix = postfix;
	}

	@Override
	public DTO handleRestriction(Restriction restriction) {
		// Object[] restrictionValues = super.handleRestrictionValue(restriction.getValues());
		Object[] restrictionValues = restriction.getValues();

		if (restrictionValues.length > 1)
			throw new IllegalArgumentException("'" + restriction.getValues() + "' restriction cannot accept more than one values.");

		final StringBuilder builder = new StringBuilder();
		final Nullable nullable = restriction.getNullable();

		final String restrictionId = restriction.getId();
		final String fieldToRestrict = restriction.getField();
		final Object restrictionValue = restrictionValues[0];

		switch (nullable) {
			case DISCARD:
				boolean handleRestriction = false;
				if (restrictionValue instanceof String) {
					final String strValue = ((String) restrictionValue);
					handleRestriction = (!strValue.isEmpty());
				} else handleRestriction = (restrictionValue != null);

				if (handleRestriction) {
					builder.append(super.getEntityAliasName()).append(".").
						append(fieldToRestrict).append(" like concat(").
						append(this.prefix).
						append(" :").append(restrictionId).
						append(this.postfix).append(")");
					return new DTO(builder.toString(), true);
				} else {
					return new DTO(builder.toString(), false);
				}

			case KEEP:
				if (restrictionValue != null) {
					builder.append(super.getEntityAliasName()).append(".").
						append(fieldToRestrict).append(" like concat(").
						append(this.prefix).
						append(" :").append(restrictionId).
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
