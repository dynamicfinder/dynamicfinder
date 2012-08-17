package org.xsalefter.finder4j.spi;

import org.xsalefter.finder4j.Restriction;
import org.xsalefter.finder4j.RestrictionHandler;

public abstract class AbstractRestrictionHandler implements RestrictionHandler {

	private String entityName;

	public AbstractRestrictionHandler() {
		this.entityName = "";
	}

	@Override
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * Get entity name which is defined in {@link AbstractQueryBuilder}.
	 * @return {@link String} entity name.
	 */
	protected final String getEntityName() {
		return this.entityName;
	}

	/**
	 * Used to handle {@link Restriction#getValues()}. If restrictionValues 
	 * parameter is empty or null, this method will return 
	 * <code>new Object[] { null }</code>
	 * @param restrictionValues to handle.
	 * @return <code>new Object[] { null }</code> if restrictionValues parameter 
	 * is null or empty, or unmodified value restrictionValues if not null.
	 */
	protected Object[] handleRestrictionValue(Object[] restrictionValues) {
		if (restrictionValues == null || restrictionValues.length == 0)
			restrictionValues = new Object[] { null };
		return restrictionValues;
	}
}
