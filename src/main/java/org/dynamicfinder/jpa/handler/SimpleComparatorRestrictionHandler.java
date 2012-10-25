package org.dynamicfinder.jpa.handler;

import org.dynamicfinder.Nullable;
import org.dynamicfinder.Restriction;
import org.dynamicfinder.RestrictionType;
import org.dynamicfinder.spi.AbstractQueryBuilder;
import org.dynamicfinder.spi.RestrictionHandler;

/**
 * Kind of {@link RestrictionHandler} for {@link RestrictionType#EQUAL}, 
 * {@link RestrictionType#NOT_EQUAL}, {@link RestrictionType#GREATER},
 * {@link RestrictionType#GREATER_EQUAL}, {@link RestrictionType#LESSER},
 * {@link RestrictionType#LESSER_EQUAL}.
 * @author xsalefter (xsalefter[at]gmail.com)
 */
class SimpleComparatorRestrictionHandler implements RestrictionHandler {

	private final String characterComparator;

	/**
	 * Default constructor. The value of parameter could be 
	 * <code>=, !=, &gt;, &gt=, &lt;,</code> or <code>&lt=.</code>  
	 * @param characterComparator Character string as comparator.
	 */
	public SimpleComparatorRestrictionHandler(final String characterComparator) {
		super();
		this.characterComparator = characterComparator;
	}

	@Override
	public RestrictionHandler.Result handleRestriction(
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
				} else {
					handleRestriction = (restrictionValue != null);
				}

				if (handleRestriction) {
					builder.append(entityAndField).append(" ").
						append(this.characterComparator).
						append(restrictionId);
					return new Result(builder.toString(), true);
				} else {
					return new Result(builder.toString(), false); 
				}

			case KEEP:
				if (restrictionValue != null) {
					builder.append(entityAndField).append(" ").
						append(this.characterComparator).append(restrictionId);

					return new Result(builder.toString(), true);
				} else {
					if (restriction.getRestrictionType().equals(RestrictionType.EQUAL)) {
						builder.append(entityAndField).append(" is null");
					} else if (restriction.getRestrictionType().equals(RestrictionType.NOT_EQUAL)) {
						builder.append(entityAndField).append(" is not null");
					// Good references(just in case I'm forget):
					// http://www.xaprb.com/blog/2006/05/18/why-null-never-compares-false-to-anything-in-sql/
					} else {
						throw new UnsupportedOperationException("Null value only for EQUAL or NOT_EQUAL.");
					}
				}

				return new Result(builder.toString(), false);

			default:
				throw new IllegalStateException("Unknown Nullable value.");
		}	
	}

}
