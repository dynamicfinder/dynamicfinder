package org.xsalefter.finder4j.jpa.handler;

import org.xsalefter.finder4j.Nullable;
import org.xsalefter.finder4j.Restriction;
import org.xsalefter.finder4j.RestrictionType;
import org.xsalefter.finder4j.spi.AbstractRestrictionHandler;
import org.xsalefter.finder4j.spi.RestrictionHandler;

/**
 * Kind of {@link RestrictionHandler} for {@link RestrictionType#EQUAL}, 
 * {@link RestrictionType#NOT_EQUAL}, {@link RestrictionType#GREATER},
 * {@link RestrictionType#GREATER_EQUAL}, {@link RestrictionType#LESSER},
 * {@link RestrictionType#LESSER_EQUAL}.
 * @author xsalefter
 */
class SimpleComparatorRestrictionHandler extends AbstractRestrictionHandler {

	private final String characterComparator;

	/**
	 * Default constructor. The value of parameter could be 
	 * <code>=, !=, &gt;, &gt=, &lt;,</code> or <code>&lt=.</code>  
	 * @param characterComparator Character string as comparator.
	 */
	public SimpleComparatorRestrictionHandler(final String characterComparator) {
		this.characterComparator = characterComparator;
	}

	@Override
	public RestrictionHandler.DTO handleRestriction(Restriction restriction) {
		Object[] restrictionValues = super.handleRestrictionValue(restriction.getValues());

		if (restrictionValues.length > 1)
			throw new IllegalArgumentException("'" + restriction.getId() + "' restriction cannot accept more than one values.");

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
				} else if (restrictionValue instanceof Number) {
					
				} else handleRestriction = (restrictionValue != null);

				if (handleRestriction) {
					builder.append(super.getEntityAliasName()).append(".").
						append(fieldToRestrict).append(" ").
						append(this.characterComparator).append(" :").
						append(restrictionId);
					return new DTO(builder.toString(), true);
				} else return new DTO(builder.toString(), false); 

			case KEEP:
				if (restrictionValue != null) {
					builder.append(super.getEntityAliasName()).append(".").
						append(fieldToRestrict).append(" ").append(this.characterComparator).
						append(" :").append(restrictionId);

					return new DTO(builder.toString(), true);
				} else builder.
						append(super.getEntityAliasName()).append(".").
						append(fieldToRestrict).append(" is null");

				return new DTO(builder.toString(), false);

			default:
				throw new IllegalStateException("Unknown Nullable value.");
		}	
	}
}
