package org.dynamicfinder.spi;

import org.dynamicfinder.Restriction;

/**
 * <p>
 * Define how to create {@link Restriction} for each {@link RestrictionType}. 
 * The behavior/logic for some restrictions is perhaps different than others.  
 * This interface provide a way to create different {@link Restriction} string.
 * </p>
 * @author xsalefter (xsalefter[at]gmail.com)
 * @see {@link RestrictionHandler.Result}
 */
public interface RestrictionHandler {

	/**
	 * @param restriction to handle to {@link String}. Result could be 
	 * obtained via {@link Result#getRestrictionString()}.
	 * @param entityAliasName Entity alias name.
	 * @param restriction restriction to handle.
	 * @return New instance of {@link Result}.
	 */
	public abstract RestrictionHandler.Result handleRestriction(
			final AbstractQueryBuilder abstractQueryBuilder, 
			final Restriction restriction
	);

	/**
	 * Simple POJO for {@link RestrictionHandler#parseRestriction(Restriction)}.
	 * @author xsalefter (xsalefter[at]gmail.com)
	 */
	public static final class Result {
		private final String restrictionString;
		private final boolean parameterizedQueryString;

		/**
		 * Default constructor.
		 * @param restrictionString {@link String} value of parsed {@link Restriction}.
		 * @param hasParameterizedQueryString Indicated that {@link Restriction} 
		 * parsed by {@link RestrictionHandler#handleRestriction(Restriction)} 
		 * produce a (chunk of) parameterized query string.
		 */
		public Result(final String restrictionString, final boolean hasParameterizedQueryString) {
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
		 * @return true if {@link Restriction} passed in 
		 * {@link RestrictionHandler#handleRestriction(Restriction)} have  
		 * parameterized query string.
		 */
		public boolean hasParameterizedQueryString() {
			return parameterizedQueryString;
		}

	}
}
