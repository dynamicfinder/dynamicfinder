package org.xsalefter.finder4j.spi;

import org.xsalefter.finder4j.Restriction;
import org.xsalefter.finder4j.RestrictionType;

/**
 * <p>
 * Define how to create {@link Restriction} for each {@link RestrictionType}. 
 * the behavior/logic for some restrictions is perhaps different than others, so 
 * this interface provide a way to create different {@link Restriction} string 
 * (could be obtained by calling {@link RestrictionHandler.DTO}).
 * </p>
 * @author xsalefter (xsalefter@gmail.com)
 */
public interface RestrictionHandler {

	/**
	 * Set the entity name (table, entity class).
	 * @param entityName to set.
	 */
	void setEntityAliasName(String entityName);

	/**
	 * @param restriction to handle to {@link String}. Result could be 
	 * obtained via {@link DTO#getRestrictionString()}.
	 * @return New instance of {@link DTO}.
	 */
	RestrictionHandler.DTO handleRestriction(Restriction restriction);

	/**
	 * Data Transfer Object for {@link RestrictionHandler#parseRestriction(Restriction)}.
	 * @author xsalefter
	 */
	public static class DTO {
		private final String restrictionString;
		private final boolean parameterizedQueryString;

		/**
		 * Default constructor.
		 * @param restrictionString {@link String} value of parsed {@link Restriction}.
		 * @param hasParameterizedQueryString Indicated that {@link Restriction} 
		 * parsed by {@link RestrictionHandler#handleRestriction(Restriction)} 
		 * produce a (chunk of) parameterized query string.
		 */
		public DTO(final String restrictionString, final boolean hasParameterizedQueryString) {
			super();
			this.restrictionString = restrictionString;
			this.parameterizedQueryString = hasParameterizedQueryString;
		}

		/**
		 * Get parsed {@link Restriction} as a {@link String}.
		 * @return {@link String} version of {@link Restriction}.
		 */
		public String getRestrictionString() {
			return restrictionString;
		}

		/**
		 * Indicated that {@link Restriction} parsed by 
		 * {@link RestrictionHandler#handleRestriction(Restriction)} produce a 
		 * (chunk of) parameterized query string.
		 * 
		 * @return true if parsed {@link Restriction} produce a parameterized 
		 * query string.
		 */
		public boolean hasParameterizedQueryString() {
			return parameterizedQueryString;
		}

	}
}
