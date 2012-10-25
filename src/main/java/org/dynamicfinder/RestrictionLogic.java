package org.dynamicfinder;

/**
 * Define {@link RestrictionLogic} for {@link Restriction}.
 * @author xsalefter (xsalefter[at]gmail.com)
 */
public enum RestrictionLogic {

	/** 
	 * Default value for {@link RestrictionLogic}. Append 'and' string to 
	 * restriction string. If {@link Restriction} position is in the end of 
	 * whole restriction, the {@link QueryBuilder} implementation would ignore 
	 * this logic.
	 */
	AND,

	/**
	 * Append 'or' string to restriction string. If {@link Restriction} 
	 * position is in the end of whole restriction, the {@link QueryBuilder} 
	 * implementation would ignore this logic.
	 */
	OR;

	/**
	 * Get the lower-case version of this {@link RestrictionLogic#toString()}.
	 * @return {@link String#toLowerCase()} version of {@link RestrictionLogic#toString()}.
	 */
	public String toLowerCase() {
		return this.toString().toLowerCase();
	}

	/**
	 * Get the upper-case version of this {@link RestrictionLogic#toString()}.
	 * @return {@link String#toLowerCase()} version of {@link RestrictionLogic#toString()}.
	 */
	public String toUpperCase() {
		return this.toString().toUpperCase();
	}
}
