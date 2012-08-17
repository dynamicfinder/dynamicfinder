package org.xsalefter.finder4j;

/**
 * Used by {@link QueryBuilder} intensively, this interface define how to create 
 * {@link Restriction} for each {@link RestrictionType}. 
 * @author xsalefter
 *
 */
public interface RestrictionHandler {

	/**
	 * Set the entity name.
	 * @param entityName to set.
	 */
	void setEntityName(final String entityName);

	/**
	 * @param restriction to handle to {@link String}.
	 * @return New instance of {@link DTO}.
	 */
	RestrictionHandler.DTO parseRestriction(Restriction restriction);

	/**
	 * Data Transfer Object for {@link RestrictionHandler#parseRestriction(Restriction)}.
	 * @author xsalefter
	 */
	public static class DTO {
		private final String restrictionString;
		private final boolean needParameter;

		/**
		 * Default constructor.
		 * @param restrictionString {@link String} value of parsed {@link Restriction}.
		 * @param needParameter indicate that the parsed {@link Restriction}, 
		 * which is added to restrictionString parameter need a parameter 
		 * defined in {@link Restriction} it self.
		 */
		public DTO(final String restrictionString, final boolean needParameter) {
			super();
			this.restrictionString = restrictionString;
			this.needParameter = needParameter;
		}

		/**
		 * Get parsed {@link Restriction} as a {@link String}.
		 * @return {@link String} version of {@link Restriction}.
		 */
		public String getRestrictionString() {
			return restrictionString;
		}

		/**
		 * Indicated that {@link Restriction} passed in constructor parameter 
		 * really need parameter to pass to data API (JPA/JDBC) parameter. On the
		 * other words, passed {@link Restriction} doesn't produce hard-coded 
		 * restriction (like is null, etc) when parsed by 
		 * {@link RestrictionHandler#parseRestriction(Restriction)}. 
		 * @return true if passed {@link Restriction} in constructor parameter 
		 * need a parameter to added. 
		 */
		public boolean isNeedParameter() {
			return needParameter;
		}

	}
}
