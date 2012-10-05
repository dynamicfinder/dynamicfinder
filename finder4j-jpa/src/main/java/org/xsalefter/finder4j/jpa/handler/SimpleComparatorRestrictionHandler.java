package org.xsalefter.finder4j.jpa.handler;

import org.xsalefter.finder4j.Nullable;
import org.xsalefter.finder4j.Restriction;
import org.xsalefter.finder4j.RestrictionType;
import org.xsalefter.finder4j.spi.AbstractQueryBuilder;
import org.xsalefter.finder4j.spi.RestrictionHandler;

/**
 * Kind of {@link RestrictionHandler} for {@link RestrictionType#EQUAL}, 
 * {@link RestrictionType#NOT_EQUAL}, {@link RestrictionType#GREATER},
 * {@link RestrictionType#GREATER_EQUAL}, {@link RestrictionType#LESSER},
 * {@link RestrictionType#LESSER_EQUAL}.
 * @author xsalefter (xsalefter@gmail.com)
 */
class SimpleComparatorRestrictionHandler extends RestrictionHandler {

	private final String characterComparator;

	/**
	 * Default constructor. The value of parameter could be 
	 * <code>=, !=, &gt;, &gt=, &lt;,</code> or <code>&lt=.</code>  
	 * @param characterComparator Character string as comparator.
	 */
	public SimpleComparatorRestrictionHandler(
		final AbstractQueryBuilder queryBuilder, 
		final String characterComparator) {

		super(queryBuilder);
		this.characterComparator = characterComparator;
	}

	@Override
	public RestrictionHandler.DTO handleRestriction(Restriction restriction) {
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
				} else if (restrictionValue instanceof Number) {
					
				} else handleRestriction = (restrictionValue != null);

				if (handleRestriction) {
					builder.append(entityAndField).append(" ").
						append(this.characterComparator).
						append(restrictionId);
					return new DTO(builder.toString(), true);
				} else return new DTO(builder.toString(), false); 

			case KEEP:
				if (restrictionValue != null) {
					builder.append(entityAndField).append(" ").
						append(this.characterComparator).append(restrictionId);

					return new DTO(builder.toString(), true);
				} else builder.
						append(entityAndField).append(" is null");

				return new DTO(builder.toString(), false);

			default:
				throw new IllegalStateException("Unknown Nullable value.");
		}	
	}

}
