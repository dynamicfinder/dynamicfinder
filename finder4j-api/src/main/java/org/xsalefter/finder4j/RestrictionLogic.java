package org.xsalefter.finder4j;

/**
 * Define {@link RestrictionLogic} after {@link Restriction} object parsed as JPQL.
 */
public enum RestrictionLogic {
	AND { 
		public String toString() { return "and"; }
	}, 
	OR {
		public String toString() { return "or"; }
	};

	/**
	 * Do the same thing like {@link Enum#valueOf(String)} and set 
	 * restrictionString parameter to upper case.
	 * @param restrictionLogic to parse as {@link RestrictionLogic}.
	 * @return {@link RestrictionLogic} instance.
	 */
	public static final RestrictionLogic of(final String restrictionLogic) {
		return RestrictionLogic.valueOf(restrictionLogic.toUpperCase());
	}
}
